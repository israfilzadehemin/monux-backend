package com.budgetmanagementapp.model.user;

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
public class ConfirmOtpRsModel {
    String username;

    String otpId;

    @Schema(example = "used")
    String otpStatus;

    @Schema(example = "2021-10-23 12:20")
    LocalDateTime otpCreationDateTime;
}
