package com.budgetmanagementapp.model.faq;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqRqModel {
    @NotBlank
    @Size(max = 5000)
    String questionAz;

    @NotBlank
    @Size(max = 5000)
    String questionEn;

    @NotBlank
    @Size(max = 5000)
    String questionRu;

    @NotBlank
    @Size(max = 5000)
    String answerAz;

    @NotBlank
    @Size(max = 5000)
    String answerEn;

    @NotBlank
    @Size(max = 5000)
    String answerRu;

}
