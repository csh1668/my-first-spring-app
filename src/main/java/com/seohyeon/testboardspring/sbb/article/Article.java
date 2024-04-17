package com.seohyeon.testboardspring.sbb.article;

import com.seohyeon.testboardspring.sbb.BaseEntity;
import com.seohyeon.testboardspring.sbb.comment.Comment;
import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@Setter
@Getter
public abstract class Article extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    protected String content;
    @ManyToMany
    protected Set<SiteUser> voter;

    protected Article(){
        super();
    }
}
