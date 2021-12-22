package com.budgetmanagementapp.model.transaction;

import javax.validation.constraints.NotBlank;

import com.budgetmanagementapp.model.transaction.TransactionRqModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtRqModel extends TransactionRqModel {
    @ApiModelProperty(
            name = "accountId",
            dataType = "string",
            example = "500de72f-7e0d-4fa9-bcca-4069629c2648")
    @NotBlank
    String accountId;
}
