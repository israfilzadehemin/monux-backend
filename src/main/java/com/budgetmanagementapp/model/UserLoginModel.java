package com.budgetmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserLoginModel {
    private String username;
    private String password;
    private Boolean rememberMe;
}
