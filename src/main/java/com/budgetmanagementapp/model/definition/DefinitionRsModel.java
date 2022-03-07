package com.budgetmanagementapp.model.definition;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefinitionRsModel {
    String definitionId;
    Object title;
    Object text;
    String icon;
}
