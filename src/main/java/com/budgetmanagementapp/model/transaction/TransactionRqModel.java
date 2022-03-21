package com.budgetmanagementapp.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRqModel {
    @Schema(example = "2021-10-23 04:58")
    @NotBlank
    String dateTime;

    @Schema(example = "200")
    @NotNull
    BigDecimal amount;

    @NotNull
    @Size(max = 5000)
    String description;
}
