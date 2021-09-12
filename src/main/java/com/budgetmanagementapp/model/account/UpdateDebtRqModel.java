package com.budgetmanagementapp.model.account;

import javax.validation.constraints.NotBlank;

import com.budgetmanagementapp.model.transaction.DebtRqModel;
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
public class UpdateDebtRqModel extends DebtRqModel {

    @NotBlank
    String transactionId;
}