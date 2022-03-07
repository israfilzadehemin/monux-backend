package com.budgetmanagementapp.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "2020-10-23 04:58")
    @NotBlank
    String dateTimeFrom;

    @Schema(example = "2021-10-23 04:58")
    @NotBlank
    String dateTimeTo;

}
