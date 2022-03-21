package com.budgetmanagementapp.model.transaction;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmountListRsModel {
    Map<?, ?> income;
    Map<?, ?> outgoing;
}
