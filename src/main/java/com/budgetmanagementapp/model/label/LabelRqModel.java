package com.budgetmanagementapp.model.label;

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
public class LabelRqModel {
    @Schema(example = "food", required = true)
    @NotBlank
    String labelName;

    @Schema(example = "income", required = true)
    @NotBlank
    String labelCategory;
}
