package com.budgetmanagementapp.model.feature;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeatureRqModel {
    @NotBlank
    @Size(max = 5000)
    String contentAz;

    @NotBlank
    @Size(max = 5000)
    String contentEn;

    @NotBlank
    @Size(max = 5000)
    String contentRu;

}
