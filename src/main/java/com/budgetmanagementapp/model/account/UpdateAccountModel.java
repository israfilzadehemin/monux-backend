package com.budgetmanagementapp.model.account;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountModel {
    @ApiModelProperty(name = "newAccountName", dataType = "string", example = "New Account", required = true)
    @NotBlank
    String newAccountName;

    @ApiModelProperty(name = "accountTypeName", dataType = "string", example = "savingAccount", required = true)
    @NotBlank
    String accountTypeName;
}
