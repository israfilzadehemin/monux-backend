package com.budgetmanagementapp.model;

import java.time.LocalDateTime;
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
public class UserRsModel {
    String userId;
    String username;
    String fullName;
    LocalDateTime creationDateTime;
    String status;
    String paymentStatus;

}
