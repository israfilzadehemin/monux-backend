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
public class UpdateCategoryRqModel extends CategoryRqModel {
    @ApiModelProperty(name = "categoryType", dataType = "string", example = "81c8fcc0-..")
    @NotBlank
    String categoryId;
}
