package com.budgetmanagementapp.model.user;

import javax.validation.constraints.NotBlank;

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
public class ConfirmOtpRqModel {
    @ApiModelProperty(
            name = "username",
            dataType = "string")
    @NotBlank
    String username;

    @ApiModelProperty(
            name = "otp",
            dataType = "string")
    @NotBlank
    String otp;
}
