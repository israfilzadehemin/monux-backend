package com.budgetmanagementapp.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(required = true)
    String receiverAccountId;

    @Schema(required = true)
    String senderAccountId;

    @Schema(example = "1.7", required = true)
    Double rate;
}
