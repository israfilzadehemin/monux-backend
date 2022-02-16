package com.budgetmanagementapp.model.transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteTransactionRqModel {
    @ApiModelProperty(name = "transactionIds", dataType = "list of string")
    List<String> transactionIds;
}
