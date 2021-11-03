package com.budgetmanagementapp.model.definition;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefinitionRqModel {
    @ApiModelProperty(
            name = "title",
            dataType = "string")
    @NotBlank
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    @NotBlank
    String text;

    @ApiModelProperty(
            name = "icon",
            dataType = "string")
    @NotBlank
    String icon;
}
