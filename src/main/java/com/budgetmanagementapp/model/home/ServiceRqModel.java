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
public class ServiceRqModel {
    @ApiModelProperty(
            name = "title",
            dataType = "string",
            example = "Home Page",
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
}
