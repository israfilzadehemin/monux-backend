package com.budgetmanagementapp.model.transaction;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmountListRsModel {
    List<BigDecimal> incomeAmounts;
    List<BigDecimal> outgoingAmounts;
}
