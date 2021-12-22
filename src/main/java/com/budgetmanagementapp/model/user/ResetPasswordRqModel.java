package com.budgetmanagementapp.model.user;

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
public class ResetPasswordRqModel {
    @ApiModelProperty(
            name = "password",
            dataType = "string")
    @NotBlank
    @Size(min = 5)
    String password;

    @ApiModelProperty(
            name = "confirmPassword",
            dataType = "string")
    @NotBlank
    @Size(min = 5)
    String confirmPassword;
}
