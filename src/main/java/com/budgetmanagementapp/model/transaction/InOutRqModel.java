package com.budgetmanagementapp.model.transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InOutRqModel extends TransactionRqModel {
    @ApiModelProperty(name = "accountId", dataType = "string")
    @NotBlank
    String accountId;

    @ApiModelProperty(name = "categoryId", dataType = "string")
    @NotBlank
    String categoryId;

    @ApiModelProperty(name = "categoryId", dataType = "list of string")
    @NotNull
    List<String> labelIds;
}
