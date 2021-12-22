package com.budgetmanagementapp.model.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRsModel {
    @ApiModelProperty(
            name = "categoryType",
            dataType = "string")
    String categoryId;

    @ApiModelProperty(
            name = "icon",
            dataType = "string")
    @NotBlank
    String icon;

    @ApiModelProperty(
            name = "categoryName",
            dataType = "string",
            example = "Salary")
    @NotBlank
    String categoryName;

    @ApiModelProperty(
            name = "categoryType",
            dataType = "string",
            example = "INCOME")
    @NotBlank
    String categoryType;
}
