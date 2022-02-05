package com.budgetmanagementapp.model.account;

import com.budgetmanagementapp.model.transaction.DebtRqModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDebtRqModel extends DebtRqModel {

    @ApiModelProperty(name = "transactionId", dataType = "string", example = "663fb5d4-..c", required = true)
    @NotBlank
    String transactionId;
}
