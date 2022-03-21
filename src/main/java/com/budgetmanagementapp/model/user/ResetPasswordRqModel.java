package com.budgetmanagementapp.model.user;

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
    @NotBlank
    @Size(min = 5)
    String password;

    @NotBlank
    @Size(min = 5)
    String confirmPassword;

    @NotBlank
    @Size(min = 5, max = 5)
    String otp;
}
