package com.seohyeon.testboardspring.sbb.comment;

import com.seohyeon.testboardspring.sbb.answer.AnswerService;
import com.seohyeon.testboardspring.sbb.article.ArticleType;
import com.seohyeon.testboardspring.sbb.question.QuestionForm;
import com.seohyeon.testboardspring.sbb.question.QuestionService;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import com.seohyeon.testboardspring.sbb.user.UserService;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.expression.Strings;

import java.security.Principal;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/create/{parentType}/{parentId}")
    public String createComment(HttpServletRequest request, Model model, @PathVariable("parentId") Integer parentId,
                                @PathVariable("parentType") String parentType, @Valid CommentForm commentForm,
                                BindingResult bindingResult, Principal principal){
        var result = "redirect:" + request.getHeader("Referer");
        if (bindingResult.hasErrors()){
            return result;
        }
        var type = ArticleType.valueOf(parentType);
        SiteUser user = userService.getUser(principal.getName());
        commentService.create(type, parentId, commentForm.getContent(), user);
//        switch (type){
//            case QUESTION -> {
//                var comment = commentService.create(type, parentId, commentForm.getContent(), user);
//            }
//            case ANSWER -> {
//            }
//        }
        return result;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/modify/{id}")
    public String modifyComment(HttpServletRequest request, @PathVariable("id") Integer id,
                                @Valid QuestionForm questionForm, BindingResult bindingResult,
                                Principal principal){
        var result = "redirect:" + request.getHeader("Referer");
        if (bindingResult.hasErrors()){
            return result;
        }
        Comment comment = commentService.getComment(id);
        if (!comment.getAuthor().getUserName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.modify(comment, questionForm.getContent());
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/delete/{id}")
    public String deleteComment(HttpServletRequest request, @PathVariable("id") Integer id,
                                Principal principal){
        Comment comment = commentService.getComment(id);
        if (!comment.getAuthor().getUserName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return "redirect:" + request.getHeader("Referer");
    }
}
