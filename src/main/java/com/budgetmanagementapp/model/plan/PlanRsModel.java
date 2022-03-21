package com.budgetmanagementapp.model.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanRsModel {
    String planId;

    @Schema(required = true)
    @NotBlank
    Object title;

    @Schema(required = true)
    @NotBlank
    Object text;

    @Schema(example = "30")
    BigDecimal price;

    @Schema(example = "MONTHLY")
    String periodType;

    List<String> featuresIds;
}
