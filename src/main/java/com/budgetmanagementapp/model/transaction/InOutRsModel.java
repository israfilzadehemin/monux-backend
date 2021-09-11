package com.budgetmanagementapp.model.transaction;

import java.util.List;

import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

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
