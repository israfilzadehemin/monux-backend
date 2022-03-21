package com.budgetmanagementapp.model.transaction;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAmountListRsModel {
    @Schema(example = "2020-10-23 04:58")
    String dateTimeFrom;

    @Schema(example = "2021-10-23 04:58")
    String dateTimeTo;

    Map<?, ?> income;
    Map<?, ?> outgoing;
}
