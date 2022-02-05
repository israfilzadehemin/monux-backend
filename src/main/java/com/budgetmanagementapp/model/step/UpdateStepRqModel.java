package com.budgetmanagementapp.model.step;

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
public class UpdateStepRqModel extends StepRqModel {
    @ApiModelProperty(name = "stepId", dataType = "string", required = true)
    @NotBlank
    String stepId;

    @ApiModelProperty(name = "titleAz", dataType = "string", required = true)
    @NotBlank
    String titleAz;

    @ApiModelProperty(name = "titleEn", dataType = "string", required = true)
    @NotBlank
    String titleEn;

    @ApiModelProperty(name = "titleRu", dataType = "string", required = true)
    @NotBlank
    String titleRu;

    @ApiModelProperty(name = "textAz", dataType = "string", required = true)
    @NotBlank
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string", required = true)
    @NotBlank
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string", required = true)
    @NotBlank
    String textRu;

    @ApiModelProperty(name = "icon", dataType = "string")
    String icon;

    @ApiModelProperty(name = "color", dataType = "string", example = "E633FF")
    String color;
}
