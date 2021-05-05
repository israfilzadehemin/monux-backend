package com.budgetmanagementapp.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    INCOME,
    OUTGOING,
    TRANSFER,
    DEBT_IN,
    DEBT_OUT

}
