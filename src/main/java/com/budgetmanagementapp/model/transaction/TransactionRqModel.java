package com.budgetmanagementapp.model.transaction;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "dateTime", dataType = "string", example = "2021-10-23 04:58")
    @NotBlank
    String dateTime;

    @ApiModelProperty(name = "amount", dataType = "bigDecimal", example = "200")
    @NotNull
    BigDecimal amount;

    @ApiModelProperty(name = "description", dataType = "string")
    @NotNull
    @Size(max = 5000)
    String description;
}
