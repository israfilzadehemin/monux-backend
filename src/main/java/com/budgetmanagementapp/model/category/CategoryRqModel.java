package com.budgetmanagementapp.model.category;

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
public class CategoryRqModel {

    @ApiModelProperty(name = "icon", dataType = "string")
    @NotBlank
    String icon;

    @ApiModelProperty(name = "categoryName", dataType = "string", example = "Salary")
    @NotBlank
    String categoryName;

    @ApiModelProperty(name = "categoryType", dataType = "string", example = "INCOME")
    @NotBlank
    String categoryType;
}
