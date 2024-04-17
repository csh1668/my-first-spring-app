package com.seohyeon.testboardspring.sbb.question;

import com.seohyeon.testboardspring.sbb.answer.Answer;
import com.seohyeon.testboardspring.sbb.answer.AnswerForm;
import com.seohyeon.testboardspring.sbb.answer.AnswerService;
import com.seohyeon.testboardspring.sbb.article.ArticleType;
import com.seohyeon.testboardspring.sbb.comment.CommentForm;
import com.seohyeon.testboardspring.sbb.comment.CommentService;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import com.seohyeon.testboardspring.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, CommentForm commentForm,
                         @RequestParam(value = "answer_page", defaultValue = "0") int answer_page,
                         @RequestParam(value = "answer_order", defaultValue = "createdDate") String by){
        Question q = questionService.getQuestion(id);

        q.setCommentList(commentService.getList(ArticleType.QUESTION, id));

        model.addAttribute("question", q);
        model.addAttribute("by", by);
        System.out.println(by);
        Page<Answer> answers = answerService.getList(q, answer_page, by);

        for (var answer : answers){
            answer.setCommentList(commentService.getList(ArticleType.ANSWER, answer.getId()));
        }

        model.addAttribute("answerPaging", answers);
        var bestAnswer = answerService.getBestAnswer(q);
        model.addAttribute("bestAnswer", bestAnswer);
        return "question_detail";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/create")
    public String createQuestion(QuestionForm questionForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("question/create")
    public String createQuestion(Model model, @Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal){
        if (bindingResult.hasErrors()){
            return "question_form";
        }

        String subject = questionForm.getSubject();
        String context = questionForm.getContent();
        SiteUser user = userService.getUser(principal.getName());
        int id = this.questionService.create(subject, context, user);
        return String.format("redirect:/question/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String modifyQuestion(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
        Question question = this.questionService.getQuestion(id);
        String author = question.getAuthor().getUserName();
        System.out.println(author);
        System.out.println(principal.getName());
        if (author == null || !author.equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/modify/{id}")
    public String modifyQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id){
        if (bindingResult.hasErrors()){
            return "question_form";
        }

        Question question = this.questionService.getQuestion(id);
        SiteUser author = question.getAuthor();
        if (author == null || !author.getUserName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUserName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/vote/{id}")
    public String voteQuestion(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
