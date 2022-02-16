package com.budgetmanagementapp.model.feature;

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
public class FeatureRqModel {
    @ApiModelProperty(name = "contentAz", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String contentAz;

    @ApiModelProperty(name = "contentEn", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String contentEn;

    @ApiModelProperty(name = "contentRu", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String contentRu;

}
