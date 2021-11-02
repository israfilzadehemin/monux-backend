package com.budgetmanagementapp.model.plan;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(
            name = "title",
            dataType = "string",
            example = "Starter",
            required = true)
    @NotBlank
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string",
            required = true)
    @NotBlank
    String text;

    @ApiModelProperty(
            name = "price",
            dataType = "bigDecimal",
            example = "30",
            required = true)
    BigDecimal price;

    @ApiModelProperty(
            name = "periodType",
            dataType = "string",
            example = "MONTHLY",
            required = true)
    @NotBlank
    String periodType;

    @ApiModelProperty(
            name = "periodType",
            dataType = "list of string")
    List<String> featuresIds;
}
