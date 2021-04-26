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

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponseModel {
    String transactionId;
    LocalDateTime creationDateTime;
    BigDecimal amount;
    String description;
    String transactionType;
    String accountId;
    String categoryId;
    List<String> tagIds;
    String oppositeAccountId;
}
