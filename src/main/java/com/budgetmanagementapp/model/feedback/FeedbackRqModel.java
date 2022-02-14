package com.budgetmanagementapp.model.feedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRqModel {
    @ApiModelProperty(name = "description", dataType = "string")
    @NotBlank
    @Size(max = 5000)
    String description;
}
