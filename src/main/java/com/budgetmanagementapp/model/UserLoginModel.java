package com.budgetmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginModel {
    private final String username;
    private final String password;
    private final Boolean rememberMe;
}
