package com.budgetmanagementapp.model.account;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(
            name = "accountTypeId",
            dataType = "string")
    String accountTypeId;

    @ApiModelProperty(
            name = "accountTypeName",
            dataType = "string",
            example = "savingAccount")
    String accountTypeName;
}
