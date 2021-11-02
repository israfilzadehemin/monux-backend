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
public class StepRqModel {
    @ApiModelProperty(
            name = "title",
            dataType = "string",
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
            name = "icon",
            dataType = "string")
    String icon;

    @ApiModelProperty(
            name = "color",
            dataType = "string",
            example = "E633FF")
    String color;
}
