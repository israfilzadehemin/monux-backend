package com.budgetmanagementapp.model.transaction;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InOutRsModel extends TransactionRsModel {
    String accountId;
    String categoryId;
    List<String> labelIds;
}
