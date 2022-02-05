package com.budgetmanagementapp.model.transfer;

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
public class UpdateTransferRqModel extends TransferRqModel {
    @ApiModelProperty(name = "transactionId", dataType = "string", example = "663fb5d4-..-", required = true)
    @NotBlank
    String transactionId;
}
