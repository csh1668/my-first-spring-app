package com.seohyeon.testboardspring.sbb.comment;

import com.seohyeon.testboardspring.sbb.article.ArticleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수입력 항목입니다.")
    private String content;
}
