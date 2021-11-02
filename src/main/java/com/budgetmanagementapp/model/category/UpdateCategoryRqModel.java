package com.budgetmanagementapp.model.category;

import javax.validation.constraints.NotBlank;

import com.budgetmanagementapp.model.category.CategoryRqModel;
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
public class UpdateCategoryRqModel extends CategoryRqModel {
    @ApiModelProperty(
            name = "categoryType",
            dataType = "string",
            example = "81c8fcc0-3b3d-11ec-8d3d-0242ac130003")
    @NotBlank
    String categoryId;
}
