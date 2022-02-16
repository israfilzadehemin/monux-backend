package com.budgetmanagementapp.model.faq;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqRsModel {
    @ApiModelProperty(name = "faqId", dataType = "string")
    String faqId;

    @ApiModelProperty(name = "question", dataType = "string")
    @NotBlank
    Object question;

    @ApiModelProperty(name = "answer", dataType = "string")
    @NotBlank
    Object answer;
}
