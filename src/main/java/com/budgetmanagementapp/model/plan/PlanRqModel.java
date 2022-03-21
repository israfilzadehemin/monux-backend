package com.budgetmanagementapp.model.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanRqModel {
    @Schema(required = true)
    @NotBlank
    String titleAz;

    @Schema(required = true)
    @NotBlank
    String titleEn;

    @Schema(required = true)
    @NotBlank
    String titleRu;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textAz;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textEn;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textRu;

    @Schema(example = "30", required = true)
    BigDecimal price;

    @Schema(example = "MONTHLY", required = true)
    @NotBlank
    String periodType;

    List<String> featuresIds;
}
