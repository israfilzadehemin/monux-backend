package com.budgetmanagementapp.model.transaction;

import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TO_SELF_MSG;

import com.budgetmanagementapp.exception.TransferToSelfException;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
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

    @AssertFalse(message = "Sender and receiver accounts are the same")
    public boolean isTrue() {
        return receiverAccountId.equals(senderAccountId);
    }
}
