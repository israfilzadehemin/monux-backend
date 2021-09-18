package com.budgetmanagementapp.model.transaction;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.TreeMap;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmountListRsModel {
    TreeMap<?, ?> incomeAmounts;
    TreeMap<?, ?> outgoingAmounts;
}
