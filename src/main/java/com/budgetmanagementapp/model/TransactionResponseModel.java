package com.budgetmanagementapp.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class TransactionResponseModel {
    String transactionId;
    LocalDateTime dateTime;
    BigDecimal amount;
    String description;
    String type;
    String senderAccountId;
    String receiverAccountId;
    String categoryId;
    List<String> tagIds;
}
