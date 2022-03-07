package com.budgetmanagementapp.model.step;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StepRsModel {
    String stepId;
    Object title;
    Object text;
    String icon;
    @Schema(example = "E633FF")
    String color;
}
