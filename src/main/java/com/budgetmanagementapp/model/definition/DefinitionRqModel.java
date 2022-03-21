package com.budgetmanagementapp.model.definition;

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
public class DefinitionRqModel {
    @NotBlank
    String titleAz;

    @NotBlank
    String titleEn;

    @NotBlank
    String titleRu;

    @NotBlank
    @Size(max = 5000)
    String textAz;

    @NotBlank
    @Size(max = 5000)
    String textEn;

    @NotBlank
    @Size(max = 5000)
    String textRu;

    @NotBlank
    String icon;
}
