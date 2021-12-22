package com.budgetmanagementapp.model.transaction;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
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
public class TransactionRsModel {
    @ApiModelProperty(
            name = "transactionId",
            dataType = "string")
    String transactionId;

    @ApiModelProperty(
            name = "dateTime",
            dataType = "localDateTime")
    LocalDateTime dateTime;

    @ApiModelProperty(
            name = "amount",
            dataType = "bigDecimal")
    BigDecimal amount;

    @ApiModelProperty(
            name = "description",
            dataType = "string")
    String description;

    @ApiModelProperty(
            name = "type",
            dataType = "string")
    String type;

    @ApiModelProperty(
            name = "senderAccountId",
            dataType = "string")
    @JsonInclude(NON_NULL)
    String senderAccountId;

    @ApiModelProperty(
            name = "receiverAccountId",
            dataType = "string")
    @JsonInclude(NON_NULL)
    String receiverAccountId;

    @ApiModelProperty(
            name = "categoryId",
            dataType = "string")
    @JsonInclude(NON_NULL)
    String categoryId;

    @ApiModelProperty(
            name = "labelIds",
            dataType = "list of string")
    @JsonInclude(NON_NULL)
    List<String> labelIds;
}
