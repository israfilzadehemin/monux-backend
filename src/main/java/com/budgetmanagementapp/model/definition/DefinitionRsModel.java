package com.budgetmanagementapp.model.definition;

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
public class DefinitionRsModel {
    @ApiModelProperty(
            name = "categoryType",
            dataType = "string")
    String definitionId;

    @ApiModelProperty(
            name = "title",
            dataType = "string",
            example = "")
    @NotBlank
    Object title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    @NotBlank
    Object text;

    @ApiModelProperty(
            name = "icon",
            dataType = "string")
    @NotBlank
    String icon;
}
