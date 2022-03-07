package com.budgetmanagementapp.model.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountModel {
    @Schema(example = "New Account", required = true)
    @NotBlank
    String newAccountName;

    @Schema(example = "savingAccount", required = true)
    @NotBlank
    String accountTypeName;
}
