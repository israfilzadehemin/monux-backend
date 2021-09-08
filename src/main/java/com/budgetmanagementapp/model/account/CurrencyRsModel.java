package com.budgetmanagementapp.model.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyRsModel {
    String currencyId;
    String currencyName;
}
