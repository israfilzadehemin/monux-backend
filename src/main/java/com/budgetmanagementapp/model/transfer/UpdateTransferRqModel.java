package com.budgetmanagementapp.model.transfer;

import javax.validation.constraints.NotBlank;

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
public class UpdateTransferRqModel extends TransferRqModel {
    @ApiModelProperty(
            name = "transactionId",
            dataType = "string",
            example = "663fb5d4-c3c1-4bc9-869f-3f2d17cfd3ac",
            required = true)
    @NotBlank
    String transactionId;
}
