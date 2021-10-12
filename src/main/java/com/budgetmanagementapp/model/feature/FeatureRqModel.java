package com.budgetmanagementapp.model.feature;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeatureRqModel {
    String content;
}
