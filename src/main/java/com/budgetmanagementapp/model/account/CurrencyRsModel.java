package com.budgetmanagementapp.model.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyRsModel {
    @ApiModelProperty(name = "currencyId", dataType = "string")
    String currencyId;

    @ApiModelProperty(name = "currencyName", dataType = "string", example = "AZN")
    String currencyName;
}
