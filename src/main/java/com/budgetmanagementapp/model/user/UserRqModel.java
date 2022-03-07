package com.budgetmanagementapp.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "example@gmail.com")
    @NotBlank
    String username;

    @NotBlank
    String fullName;
}
