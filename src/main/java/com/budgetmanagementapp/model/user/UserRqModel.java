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
public class UserRqModel {
    @ApiModelProperty(
            name = "username",
            dataType = "string",
            example = "example@gmail.com")
    @NotBlank
    String username;

    @ApiModelProperty(
            name = "fullName",
            dataType = "string")
    @NotBlank
    String fullName;
}
