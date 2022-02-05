package com.budgetmanagementapp.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginModel {
    @ApiModelProperty(name = "username", dataType = "string")
    @NotBlank
    String username;

    @ApiModelProperty(name = "password", dataType = "string")
    @NotBlank
    String password;

    @ApiModelProperty(name = "rememberMe", dataType = "boolean", example = "true")
    Boolean rememberMe;
}
