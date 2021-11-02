package com.budgetmanagementapp.model.feature;

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
public class UpdateFeatureRqModel extends FeatureRqModel{
    @ApiModelProperty(
            name = "featureId",
            dataType = "string",
            example = "")
    @NotBlank
    String featureId;
}
