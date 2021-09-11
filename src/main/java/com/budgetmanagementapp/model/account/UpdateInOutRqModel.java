package com.budgetmanagementapp.model.account;

import javax.validation.constraints.NotBlank;

import com.budgetmanagementapp.model.transaction.InOutRqModel;
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

    @NotBlank
    String transactionId;

}
