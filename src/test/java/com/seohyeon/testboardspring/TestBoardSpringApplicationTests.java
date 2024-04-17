package com.seohyeon.testboardspring;

import com.seohyeon.testboardspring.sbb.answer.AnswerRepository;
import com.seohyeon.testboardspring.sbb.question.Question;
import com.seohyeon.testboardspring.sbb.question.QuestionRepository;
import com.seohyeon.testboardspring.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class TestBoardSpringApplicationTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;

//    @Test
//    @Transactional
//    void testJpa() {
//        questionRepository.findAll().forEach(x -> System.out.println(x.toString()));
//    }

//    @Test
//    void testFindBySubjectList(){
//        /*
//        sbb% = sbb로 시작하는 문자열
//        %sbb = sbb로 끝나는 문자열
//        %sbb% = sbb를 포함하는 문자열
//         */
//        List<Question> qList = questionRepository.findBySubjectLike("%sbb%");
//        Question q = qList.get(0);
//        System.out.println(q.getSubject());
//    }

    @Test
    void testLargeJpa(){
        for (int i = 1; i <= 300; i++){
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용 없음.";
            // this.questionService.create(subject, content);
        }
    }

}
