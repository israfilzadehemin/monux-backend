package com.budgetmanagementapp.model.account;

import java.math.BigDecimal;
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
public class UpdateBalanceRqModel {

    @ApiModelProperty(name = "balance", dataType = "bigDecimal", example = "500", required = true)
    @NotNull
    BigDecimal balance;
}
