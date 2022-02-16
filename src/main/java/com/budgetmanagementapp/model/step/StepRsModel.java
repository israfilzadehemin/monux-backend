package com.budgetmanagementapp.model.step;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StepRsModel {
    @ApiModelProperty(
            name = "stepId",
            dataType = "string")
    String stepId;
    @ApiModelProperty(
            name = "title",
            dataType = "string")
    Object title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    Object text;

    @ApiModelProperty(
            name = "icon",
            dataType = "string")
    String icon;

    @ApiModelProperty(
            name = "color",
            dataType = "string",
            example = "E633FF")
    String color;
}
