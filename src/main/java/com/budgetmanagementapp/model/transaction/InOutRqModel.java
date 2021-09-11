package com.budgetmanagementapp.model.transaction;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.budgetmanagementapp.model.transaction.TransactionRqModel;
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

    @NotBlank
    String accountId;

    @NotBlank
    String categoryId;

    @NotNull
    List<String> labelIds;
}
