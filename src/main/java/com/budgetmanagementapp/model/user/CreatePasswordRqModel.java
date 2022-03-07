package com.budgetmanagementapp.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePasswordRqModel {
    @Schema(example = "israfilzadehemin@gmail.com")
    @NotBlank
    String username;

    @NotBlank
    @Size(min = 5)
    String password;

    @NotBlank
    @Size(min = 5)
    String confirmPassword;
}
