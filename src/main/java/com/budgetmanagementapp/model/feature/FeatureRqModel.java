package com.budgetmanagementapp.model.feature;

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
public class FeatureRqModel {
    @NotBlank
    String content;
}
