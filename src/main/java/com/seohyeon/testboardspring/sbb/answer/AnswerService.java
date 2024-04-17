package com.seohyeon.testboardspring.sbb.answer;

import com.seohyeon.testboardspring.sbb.exceptions.DataNotFoundException;
import com.seohyeon.testboardspring.sbb.question.Question;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Page<Answer> getList(Question question, int page, String by){
        Pageable pageable = PageRequest.of(page, 10);
        if (by.equals("createdDate"))
            return answerRepository.findAllByQuestionOrderByCreatedDateDesc(question, pageable);
        else
            return answerRepository.findAllByQuestionOrderByVoterSize(question, pageable);
    }

    public Answer create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id){
        Optional<Answer> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Answer getBestAnswer(Question question){
        var bestAnswer = this.answerRepository.findTopByVoterSize(question.getId());
        return bestAnswer.orElse(null);
    }

    public void modify(Answer answer, String content){
        answer.setContent(content);
        answer.setModifiedDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer){
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser){
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
