package com.budgetmanagementapp.model.transfer;

import com.budgetmanagementapp.model.transaction.TransactionRqModel;
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
public class TransferRqModel extends TransactionRqModel {
    @ApiModelProperty(name = "receiverAccountId", dataType = "string", required = true)
    @NotBlank
    String receiverAccountId;

    @ApiModelProperty(name = "senderAccountId", dataType = "string", required = true)
    @NotBlank
    String senderAccountId;

    @ApiModelProperty(name = "rate", dataType = "double", example = "1.7", required = true)
    Double rate;
}
