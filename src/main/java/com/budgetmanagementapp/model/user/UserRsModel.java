package com.budgetmanagementapp.model.user;

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
public class UserRsModel {
    @ApiModelProperty(
            name = "userId",
            dataType = "string")
    String userId;

    @ApiModelProperty(
            name = "username",
            dataType = "string")
    String username;

    @ApiModelProperty(
            name = "fullName",
            dataType = "string")
    String fullName;

    @ApiModelProperty(
            name = "creationDateTime",
            dataType = "localDateTime")
    LocalDateTime creationDateTime;

    @ApiModelProperty(
            name = "status",
            dataType = "string",
            example = "active")
    String status;

    @ApiModelProperty(
            name = "paymentStatus",
            dataType = "string",
            example = "paid")
    String paymentStatus;

}
