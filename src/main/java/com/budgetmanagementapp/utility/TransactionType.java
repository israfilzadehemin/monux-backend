package com.budgetmanagementapp.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    INCOME,
    OUTCOME,
    TRANSFER,
    DEBT

}
