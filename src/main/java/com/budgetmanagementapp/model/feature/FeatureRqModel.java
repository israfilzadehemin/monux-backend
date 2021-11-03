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
public class FeatureRqModel {
    @ApiModelProperty(
            name = "content",
            dataType = "string")
    @NotBlank
    String content;
}
