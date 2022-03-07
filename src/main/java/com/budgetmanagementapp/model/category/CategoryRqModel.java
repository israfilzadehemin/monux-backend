package com.budgetmanagementapp.model.category;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotBlank
    String icon;

    @Schema(example = "Salary")
    @NotBlank
    String categoryName;

    @Schema(example = "INCOME")
    @NotBlank
    String categoryType;
}
