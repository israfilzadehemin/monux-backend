package com.budgetmanagementapp.model.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

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
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    String text;

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
