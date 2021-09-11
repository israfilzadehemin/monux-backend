package com.budgetmanagementapp.model.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountTypeRsModel {
    String accountTypeId;
    String accountTypeName;
}
