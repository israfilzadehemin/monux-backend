package com.budgetmanagementapp.model.feature;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeatureRsModel {
    @ApiModelProperty(name = "featureId", dataType = "string")
    String featureId;

    @ApiModelProperty(name = "content", dataType = "string")
    Object content;
}
