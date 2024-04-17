package com.seohyeon.testboardspring.sbb.question;

import com.seohyeon.testboardspring.sbb.answer.Answer;
import com.seohyeon.testboardspring.sbb.exceptions.DataNotFoundException;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        Specification<Question> spec = search(kw);
//        return this.questionRepository.findAll(spec, pageable);
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public Question getQuestion(Integer id){
        var question = this.questionRepository.findById(id);
        if (question.isPresent()){
            return question.get();
        }
        else{
            throw new DataNotFoundException("question not found");
        }
    }

    public Integer create(String subject, String content, SiteUser user){
        Question q = new Question(subject, content, user);

        this.questionRepository.save(q);
        return q.getId();
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifiedDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    private Specification<Question> search(String kw){
        return new Specification<Question>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw+ "%"),
                        cb.like(q.get("content"), "%" + kw + "%"),
                        cb.like(u1.get("userName"), "%" + kw + "%"),
                        cb.like(a.get("content"), "%" + kw + "%"),
                        cb.like(u2.get("userName"), "%" + kw + "%"));

            }
        };
    }
}
