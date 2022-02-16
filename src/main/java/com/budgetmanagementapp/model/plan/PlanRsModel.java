package com.budgetmanagementapp.model.plan;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "planId", dataType = "string")
    String planId;

    @ApiModelProperty(name = "title", dataType = "string", required = true)
    @NotBlank
    Object title;

    @ApiModelProperty(name = "text", dataType = "string", required = true)
    @NotBlank
    Object text;

    @ApiModelProperty(name = "price", dataType = "bigDecimal", example = "30")
    BigDecimal price;

    @ApiModelProperty(name = "periodType", dataType = "string", example = "MONTHLY")
    String periodType;

    @ApiModelProperty(name = "periodType", dataType = "list of string")
    List<String> featuresIds;
}
