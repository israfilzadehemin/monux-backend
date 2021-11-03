package com.budgetmanagementapp.model.account;

import javax.validation.constraints.NotBlank;

import com.budgetmanagementapp.model.transaction.InOutRqModel;
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
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateInOutRqModel extends InOutRqModel {

    @ApiModelProperty(
            name = "transactionId",
            dataType = "string",
            example = "663fb5d4-c3c1-4bc9-869f-3f2d17cfd3ac",
            required = true)
    @NotBlank
    String transactionId;

}
