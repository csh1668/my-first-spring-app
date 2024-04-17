package com.seohyeon.testboardspring.sbb.answer;


import com.seohyeon.testboardspring.sbb.article.Article;
import com.seohyeon.testboardspring.sbb.comment.Comment;
import com.seohyeon.testboardspring.sbb.question.Question;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Answer extends Article {
    @ManyToOne
    private Question question;

    @Transient
    private List<Comment> commentList;


    public boolean isPostedBy(String name){
        return author != null && author.getUserName().equals(name);
    }

    public Answer(){
        commentList = new ArrayList<>();
    }
}
