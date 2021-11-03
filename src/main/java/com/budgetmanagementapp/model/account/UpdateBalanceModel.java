package com.budgetmanagementapp.model.account;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class UpdateBalanceModel {

    @ApiModelProperty(
            name = "accountId",
            dataType = "string",
            example = "541062a3-b99e-419b-9e2c-1daeb5fc5099",
            required = true)
    @NotBlank
    String accountId;

    @ApiModelProperty(
            name = "balance",
            dataType = "bigDecimal",
            example = "500",
            required = true)
    @NotNull
    BigDecimal balance;
}
