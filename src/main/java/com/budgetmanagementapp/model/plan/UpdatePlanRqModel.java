package com.budgetmanagementapp.model.plan;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "planId", dataType = "string")
    @NotBlank
    String planId;
}
