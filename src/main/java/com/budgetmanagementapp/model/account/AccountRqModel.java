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
public class AccountRqModel {
    @ApiModelProperty(name = "username", dataType = "string", example = "israfilzadehemin@gmail.com", required = true)
    String username;

    @ApiModelProperty(name = "accountName", dataType = "string", example = "Saving account", required = true)
    String accountName;

    @ApiModelProperty(name = "accountTypeName", dataType = "string", example = "savingAccount", required = true)
    String accountTypeName;

    @ApiModelProperty(name = "currency", dataType = "string", example = "AZN", required = true)
    String currency;

    @ApiModelProperty(name = "allowNegative", dataType = "boolean")
    Boolean allowNegative;

    @ApiModelProperty(name = "balance", dataType = "bigDecimal", example = "300")
    BigDecimal balance;

    @ApiModelProperty(name = "showInSum", dataType = "boolean")
    Boolean showInSum;
}
