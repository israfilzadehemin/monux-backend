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
    @ApiModelProperty(name = "titleAz", dataType = "string")
    @NotBlank
    String titleAz;

    @ApiModelProperty(name = "titleEn", dataType = "string")
    @NotBlank
    String titleEn;

    @ApiModelProperty(name = "titleRu", dataType = "string")
    @NotBlank
    String titleRu;

    @ApiModelProperty(name = "textAz", dataType = "string")
    @NotBlank
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string")
    @NotBlank
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string")
    @NotBlank
    String textRu;

    @ApiModelProperty(name = "icon", dataType = "string")
    @NotBlank
    String icon;
}
