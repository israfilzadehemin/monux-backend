package com.budgetmanagementapp.model.transaction;

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
    @NotBlank
    String accountId;

    @NotBlank
    String categoryId;

    @NotNull
    List<String> labelIds;
}
