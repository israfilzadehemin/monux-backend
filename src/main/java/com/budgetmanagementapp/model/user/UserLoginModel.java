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
public class UserLoginModel {
    @NotBlank
    String username;
    @NotBlank
    String password;
    Boolean rememberMe;
}
