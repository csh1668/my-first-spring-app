package com.seohyeon.testboardspring.sbb.question;

import com.seohyeon.testboardspring.sbb.answer.Answer;
import com.seohyeon.testboardspring.sbb.article.Article;
import com.seohyeon.testboardspring.sbb.comment.Comment;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question extends Article {

    @Column(length = 200)
    private String subject;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @Transient
    private List<Comment> commentList;

    public Question(String subject, String content, SiteUser author){
        this();
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    protected Question() {
        super();
        commentList = new ArrayList<>();
    }
}
