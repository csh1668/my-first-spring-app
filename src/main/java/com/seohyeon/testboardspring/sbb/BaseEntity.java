package com.seohyeon.testboardspring.sbb;

import com.seohyeon.testboardspring.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected LocalDateTime createdDate;
    protected LocalDateTime modifiedDate;
    @ManyToOne
    protected SiteUser author;

    protected BaseEntity(){
        createdDate = LocalDateTime.now();
    }
}
