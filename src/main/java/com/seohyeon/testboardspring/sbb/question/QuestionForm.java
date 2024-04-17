package com.seohyeon.testboardspring.sbb.question;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 20, message = "20자 이내로 입력하세요.")
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    @Size(max = 1000, message = "1000자 이내로 입력하세요.")
    private String content;
}
