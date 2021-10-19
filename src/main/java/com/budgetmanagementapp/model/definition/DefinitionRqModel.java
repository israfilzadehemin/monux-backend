package com.budgetmanagementapp.model.definition;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefinitionRqModel {
    @NotBlank
    String title;

    @NotBlank
    String text;

    @NotBlank
    String icon;
}
