package com.budgetmanagementapp.model.feedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRsModel {
    @ApiModelProperty(name = "feedbackId", dataType = "string")
    String feedbackId;

    @ApiModelProperty(name = "description", dataType = "string")
    String description;

    @ApiModelProperty(name = "creationDateTime", dataType = "localDateTime")
    LocalDateTime creationDateTime;

    @ApiModelProperty(name = "status", dataType = "string")
    String status;
}
