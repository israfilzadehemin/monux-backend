package com.budgetmanagementapp.model.feature;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeatureRsModel {
    String featureId;
    String content;
}
