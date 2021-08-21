package com.budgetmanagementapp.model;

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
    String password;

    @NotBlank
    @Size(min = 5)
    String confirmPassword;
}
