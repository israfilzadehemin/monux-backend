package com.budgetmanagementapp.model;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRsModel {
    String icon;
    String accountId;
    String accountName;
    String accountTypeName;
    String currency;
    Boolean allowNegative;
    BigDecimal balance;
    Boolean showInSum;
}
