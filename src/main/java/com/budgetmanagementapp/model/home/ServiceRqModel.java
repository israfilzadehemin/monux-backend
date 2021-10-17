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
public class ServiceRqModel {
    String title;
    String text;
    String icon;
}
