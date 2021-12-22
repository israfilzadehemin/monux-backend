package com.budgetmanagementapp.model.feedback;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRsModel {
    @ApiModelProperty(
            name = "feedbackId",
            dataType = "string")
    String feedbackId;

    @ApiModelProperty(
            name = "description",
            dataType = "string")
    String description;

    @ApiModelProperty(
            name = "creationDateTime",
            dataType = "localDateTime")
    LocalDateTime creationDateTime;

    @ApiModelProperty(
            name = "status",
            dataType = "string")
    String status;
}
