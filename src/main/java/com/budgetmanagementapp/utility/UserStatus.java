package com.budgetmanagementapp.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    CONFIRMED("CONFIRMED"),
    PROCESSING("PROCESSING");

    private final String userStatus;

}
