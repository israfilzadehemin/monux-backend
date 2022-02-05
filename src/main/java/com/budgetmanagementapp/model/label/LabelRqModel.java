package com.budgetmanagementapp.model.label;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRqModel {
    @ApiModelProperty(name = "labelName", dataType = "string", example = "food", required = true)
    @NotBlank
    String labelName;

    @ApiModelProperty(name = "labelCategory", dataType = "string", example = "income", required = true)
    @NotBlank
    String labelCategory;
}
