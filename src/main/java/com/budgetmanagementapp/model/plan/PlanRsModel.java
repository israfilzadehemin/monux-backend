package com.budgetmanagementapp.model.plan;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String title;
    String text;
    BigDecimal price;
    String periodType;
    List<String> featuresIds;

}
