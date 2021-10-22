package com.budgetmanagementapp.model.plan;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePlanRqModel extends PlanRqModel {

    @NotBlank
    String planId;
}
