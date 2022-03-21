package com.budgetmanagementapp.model.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
    String categoryId;
    String icon;

    @Schema(example = "Salary")
    String categoryName;

    @Schema(example = "INCOME")
    String categoryType;
}
