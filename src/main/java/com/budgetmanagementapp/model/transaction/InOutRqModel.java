package com.budgetmanagementapp.model.transaction;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.budgetmanagementapp.model.transaction.TransactionRqModel;
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
public class InOutRqModel extends TransactionRqModel {
    @ApiModelProperty(
            name = "accountId",
            dataType = "string",
            example = "500de72f-7e0d-4fa9-bcca-4069629c2648")
    @NotBlank
    String accountId;

    @ApiModelProperty(
            name = "categoryId",
            dataType = "string",
            example = "81c8fcc0-3b3d-11ec-8d3d-0242ac130003")
    @NotBlank
    String categoryId;

    @ApiModelProperty(
            name = "categoryId",
            dataType = "list of string")
    @NotNull
    List<String> labelIds;
}
