package com.budgetmanagementapp.model.transaction;


import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(
            name = "dateTimeFrom",
            dataType = "string",
            example = "2020-10-23 04:58")
    String dateTimeFrom;

    @ApiModelProperty(
            name = "dateTimeTo",
            dataType = "string",
            example = "2021-10-23 04:58")
    String dateTimeTo;

    @ApiModelProperty(
            name = "income",
            dataType = "map")
    Map<?, ?> income;

    @ApiModelProperty(
            name = "outgoing",
            dataType = "map")
    Map<?, ?> outgoing;
}
