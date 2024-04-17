package com.seohyeon.testboardspring.sbb.comment;

import com.seohyeon.testboardspring.sbb.answer.Answer;
import com.seohyeon.testboardspring.sbb.article.Article;
import com.seohyeon.testboardspring.sbb.article.ArticleType;
import com.seohyeon.testboardspring.sbb.exceptions.DataNotFoundException;
import com.seohyeon.testboardspring.sbb.question.Question;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getList(ArticleType parentType, Integer parentId){
        return commentRepository.findCommentsByParentTypeAndParentId(parentType, parentId);
    }

    public Comment getComment(Integer id){
        var commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent())
            return commentOptional.get();
        throw new DataNotFoundException(String.format("No comment found with id: %d", id));
    }

    public Comment create(ArticleType parentType, Integer parentId, String content, SiteUser user){
        Comment c = new Comment(content, user);
        c.setParentType(parentType);
        c.setParentId(parentId);

        this.commentRepository.save(c);
        return c;
    }

    public void modify(Comment comment, String content){
        comment.setContent(content);
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }
}
