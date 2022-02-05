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
public class UpdateFaqRqModel extends FaqRqModel {
    @ApiModelProperty(name = "faqId", dataType = "string")
    @NotBlank
    String faqId;
}
