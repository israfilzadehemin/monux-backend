package com.budgetmanagementapp.model.label;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRqModel {
    @ApiModelProperty(
            name = "labelName",
            dataType = "string",
            example = "food",
            required = true)
    @NotBlank
    String labelName;

    @ApiModelProperty(
            name = "labelCategory",
            dataType = "string",
            example = "income",
            required = true)
    @NotBlank
    String labelCategory;
}
