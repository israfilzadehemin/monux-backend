package com.budgetmanagementapp.model.transaction;

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
public class TransactionDateRqModel {

    @NotBlank
    String dateTimeFrom;

    @NotBlank
    String dateTimeTo;

}
