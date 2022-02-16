package com.budgetmanagementapp.model.transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRsModel extends TransactionRsModel {
    @ApiModelProperty(name = "receiverAccountId", dataType = "string", required = true)
    String receiverAccountId;

    @ApiModelProperty(name = "senderAccountId", dataType = "string", required = true)
    String senderAccountId;

    @ApiModelProperty(name = "rate", dataType = "double", example = "1.7", required = true)
    Double rate;
}
