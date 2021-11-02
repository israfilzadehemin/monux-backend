package com.budgetmanagementapp.model.transfer;

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
public class TransferRqModel extends TransactionRqModel {
    @ApiModelProperty(
            name = "receiverAccountId",
            dataType = "string",
            example = "541062a3-b99e-419b-9e2c-1daeb5fc5099",
            required = true)
    @NotBlank
    String receiverAccountId;

    @ApiModelProperty(
            name = "senderAccountId",
            dataType = "string",
            example = "500de72f-7e0d-4fa9-bcca-4069629c2648",
            required = true)
    @NotBlank
    String senderAccountId;
}
