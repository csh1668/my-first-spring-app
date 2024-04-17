package com.seohyeon.testboardspring.sbb.answer;

import com.seohyeon.testboardspring.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("select a from Answer a where a.question.id = :questionId order by size(a.voter) desc limit 1")
    Optional<Answer> findTopByVoterSize(Integer questionId);
    Page<Answer> findAllByQuestionOrderByCreatedDateDesc(Question question, Pageable pageable);
    @Query("select a from Answer a where a.question = :question order by size(a.voter) desc")
    Page<Answer> findAllByQuestionOrderByVoterSize(Question question, Pageable pageable);
}
