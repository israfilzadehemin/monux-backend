package com.budgetmanagementapp.model.label;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRsModel {
    @ApiModelProperty(name = "labelId", dataType = "string")
    String labelId;

    @ApiModelProperty(name = "labelName", dataType = "string", example = "food")
    String labelName;

    @ApiModelProperty(name = "labelCategory", dataType = "string", example = "income")
    String labelCategory;

    @ApiModelProperty(name = "visibility", dataType = "boolean", example = "true")
    boolean visibility;
}
