package com.budgetmanagementapp.model.label;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRsModel {
    String labelId;

    @Schema(example = "food")
    String labelName;

    @Schema(example = "income")
    String labelCategory;

    @Schema(example = "true")
    boolean visibility;
}
