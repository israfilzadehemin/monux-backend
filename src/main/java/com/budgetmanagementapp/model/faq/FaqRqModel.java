package com.budgetmanagementapp.model.faq;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqRqModel {
    @ApiModelProperty(
            name = "question",
            dataType = "string")
    @NotBlank
    String question;

    @ApiModelProperty(
            name = "answer",
            dataType = "string")
    @NotBlank
    String answer;
}
