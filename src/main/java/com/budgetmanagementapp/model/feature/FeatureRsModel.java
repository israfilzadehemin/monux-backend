package com.budgetmanagementapp.model.feature;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeatureRsModel {
    String featureId;
    Object content;
}
