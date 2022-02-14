package com.budgetmanagementapp.model.definition;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(max = 5000)
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String textRu;

    @ApiModelProperty(name = "icon", dataType = "string")
    @NotBlank
    String icon;
}
