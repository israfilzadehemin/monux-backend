package com.budgetmanagementapp.model.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRsModel {
    @ApiModelProperty(name = "username", dataType = "string")
    String username;

    @ApiModelProperty(name = "password", dataType = "string")
    String password;
}
