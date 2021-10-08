package com.budgetmanagementapp.model.plan;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

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
    String title;
    String text;
    BigDecimal price;
    String periodType;
    List<String> featuresIds;
}
