package com.budgetmanagementapp.model.faq;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "questionAz", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String questionAz;

    @ApiModelProperty(name = "questionEn", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String questionEn;

    @ApiModelProperty(name = "questionRu", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String questionRu;

    @ApiModelProperty(name = "answerAz", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String answerAz;

    @ApiModelProperty(name = "answerEn", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String answerEn;

    @ApiModelProperty(name = "answerRu", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String answerRu;

}
