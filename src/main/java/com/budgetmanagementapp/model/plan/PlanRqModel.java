package com.budgetmanagementapp.model.plan;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    String title;

    @NotBlank
    String text;

    BigDecimal price;

    @NotBlank
    String periodType;

    List<String> featuresIds;
}
