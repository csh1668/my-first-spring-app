package com.seohyeon.testboardspring.sbb.answer;

import com.seohyeon.testboardspring.sbb.comment.CommentForm;
import com.seohyeon.testboardspring.sbb.question.Question;
import com.seohyeon.testboardspring.sbb.question.QuestionService;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import com.seohyeon.testboardspring.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, CommentForm commentForm,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal){
        Question q = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("question", q);
            return "question_detail";
        }
        var answer = answerService.create(q, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/modify/{id}")
    public String answerModify(@ModelAttribute("answerForm") AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal){
        Answer answer = this.answerService.getAnswer(id);
        if(!answer.isPostedBy(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/modify/{id}")
    public String answerModify(@Valid @ModelAttribute("answerForm") AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal){
        if (bindingResult.hasErrors()){
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.isPostedBy(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/delete/{id}")
    public String deleteAnswer(Principal principal, @PathVariable("id") Integer id){
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.isPostedBy(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id){
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.vote(answer, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }
}
