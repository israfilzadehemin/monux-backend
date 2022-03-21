package com.budgetmanagementapp.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime paymentDate;

    @Schema(example = "active")
    String status;

    @Schema(example = "paid")
    String paymentStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(example = "az, en, ru")
    String language;

}
