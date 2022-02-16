package com.budgetmanagementapp.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmOtpRqModel {
    @ApiModelProperty(name = "username", dataType = "string")
    @NotBlank
    String username;

    @ApiModelProperty(name = "otp", dataType = "string")
    @NotBlank
    String otp;
}
