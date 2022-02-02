package com.budgetmanagementapp.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PAID("PAID"),
    NOT_PAID("NOT_PAID");

    private final String userStatus;

}
