package com.budgetmanagementapp.model.home;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StepRqModel {
    @NotBlank
    String title;

    @NotBlank
    String text;
    String icon;
    String color;
}
