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
public class AmountListRsModel {
    @ApiModelProperty(
            name = "income",
            dataType = "map")
    Map<?, ?> income;

    @ApiModelProperty(
            name = "outgoing",
            dataType = "map")
    Map<?, ?> outgoing;
}
