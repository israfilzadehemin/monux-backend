package com.budgetmanagementapp.model.home;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StepRqModel {
    String title;
    String text;
    String icon;
    String color;
}
