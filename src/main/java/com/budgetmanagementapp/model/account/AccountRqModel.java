package com.budgetmanagementapp.model.account;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "israfilzadehemin@gmail.com", required = true)
    String username;

    @Schema(example = "Saving account", required = true)
    String accountName;

    @Schema(example = "savingAccount", required = true)
    String accountTypeName;

    @Schema(example = "AZN", required = true)
    String currency;

    Boolean allowNegative;

    @Schema(example = "300")
    BigDecimal balance;

    Boolean showInSum;
}
