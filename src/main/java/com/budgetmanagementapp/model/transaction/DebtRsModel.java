package com.budgetmanagementapp.model.transaction;

import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import io.swagger.annotations.ApiModelProperty;
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
public class DebtRsModel extends TransactionRsModel {
    @ApiModelProperty(
            name = "accountId",
            dataType = "string")
    String accountId;
}
