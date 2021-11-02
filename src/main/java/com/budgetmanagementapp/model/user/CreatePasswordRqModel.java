package com.budgetmanagementapp.model.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
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
public class CreatePasswordRqModel {
    @ApiModelProperty(
            name = "username",
            dataType = "string",
            example = "israfilzadehemin@gmail.com")
    @NotBlank
    String username;

    @ApiModelProperty(
            name = "password",
            dataType = "string")
    @NotBlank
    @Size(min = 5)
    String password;

    @ApiModelProperty(
            name = "confirmPassword",
            dataType = "string")
    @NotBlank
    @Size(min = 5)
    String confirmPassword;
}
