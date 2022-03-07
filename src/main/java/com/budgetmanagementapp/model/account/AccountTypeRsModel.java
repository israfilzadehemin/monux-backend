package com.budgetmanagementapp.model.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountTypeRsModel {
    String accountTypeId;

    @Schema(example = "savingAccount")
    String accountTypeName;
}
