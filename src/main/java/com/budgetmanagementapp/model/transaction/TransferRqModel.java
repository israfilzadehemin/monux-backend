package com.budgetmanagementapp.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRqModel extends TransactionRqModel {
    @Schema(required = true)
    @NotBlank
    String receiverAccountId;

    @Schema(required = true)
    @NotBlank
    String senderAccountId;

    @Schema(example = "1.7", required = true)
    Double rate;

    @AssertFalse(message = "Sender and receiver accounts are the same")
    public boolean isTrue() {
        return receiverAccountId.equals(senderAccountId);
    }
}
