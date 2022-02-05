package com.budgetmanagementapp.model.label;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLabelRqModel extends LabelRqModel {
    @ApiModelProperty(name = "labelId", dataType = "string")
    @NotBlank
    String labelId;
}
