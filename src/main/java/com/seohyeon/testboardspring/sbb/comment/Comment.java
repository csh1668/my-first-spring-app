package com.seohyeon.testboardspring.sbb.comment;

import com.seohyeon.testboardspring.sbb.BaseEntity;
import com.seohyeon.testboardspring.sbb.article.Article;
import com.seohyeon.testboardspring.sbb.article.ArticleType;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleType parentType;

    private Integer parentId;


    public Comment(String content, SiteUser user){
        super();
        this.content = content;
        this.author = user;
    }
}
