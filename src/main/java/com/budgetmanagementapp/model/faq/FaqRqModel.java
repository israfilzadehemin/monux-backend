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
    @ApiModelProperty(name = "questionAz", dataType = "string")
    @NotBlank
    String questionAz;

    @ApiModelProperty(name = "questionEn", dataType = "string")
    @NotBlank
    String questionEn;

    @ApiModelProperty(name = "questionRu", dataType = "string")
    @NotBlank
    String questionRu;

    @ApiModelProperty(name = "answerAz", dataType = "string")
    @NotBlank
    String answerAz;

    @ApiModelProperty(name = "answerEn", dataType = "string")
    @NotBlank
    String answerEn;

    @ApiModelProperty(name = "answerRu", dataType = "string")
    @NotBlank
    String answerRu;

}
