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
public class InOutRsModel extends TransactionRsModel {
    @ApiModelProperty(name = "accountId", dataType = "string")
    String accountId;

    @ApiModelProperty(name = "categoryId", dataType = "string")
    String categoryId;

    @ApiModelProperty(name = "categoryId", dataType = "list of string")
    List<String> labelIds;
}
