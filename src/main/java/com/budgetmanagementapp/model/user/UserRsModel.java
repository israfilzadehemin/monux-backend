package com.budgetmanagementapp.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "userId", dataType = "string")
    String userId;

    @ApiModelProperty(name = "username", dataType = "string")
    String username;

    @ApiModelProperty(name = "fullName", dataType = "string")
    String fullName;

    @ApiModelProperty(name = "creationDateTime", dataType = "localDateTime")
    LocalDateTime creationDateTime;

    @ApiModelProperty(name = "paymentDate", dataType = "localDateTime")
    LocalDateTime paymentDate;

    @ApiModelProperty(name = "status", dataType = "string", example = "active")
    String status;

    @ApiModelProperty(name = "paymentStatus", dataType = "string", example = "paid")
    String paymentStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "language", dataType = "string", example = "az, en, ru")
    String language;

}
