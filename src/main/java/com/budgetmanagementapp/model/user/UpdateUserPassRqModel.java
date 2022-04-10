package com.budgetmanagementapp.model.user;

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
public class UpdateUserPassRqModel {
    @NotBlank
    String password;

    @NotBlank
    String confirmPassword;
}
