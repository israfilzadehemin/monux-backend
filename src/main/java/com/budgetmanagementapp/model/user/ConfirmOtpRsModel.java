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
public class ConfirmOtpRsModel {
    @ApiModelProperty(
            name = "username",
            dataType = "string")
    String username;

    @ApiModelProperty(
            name = "otpId",
            dataType = "string")
    String otpId;

    @ApiModelProperty(
            name = "otpStatus",
            dataType = "string",
            example = "used")
    String otpStatus;

    @ApiModelProperty(
            name = "otpStatus",
            dataType = "localDateTime",
            example = "2021-10-23 12:20")
    LocalDateTime otpCreationDateTime;
}
