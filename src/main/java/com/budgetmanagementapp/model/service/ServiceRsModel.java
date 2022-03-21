package com.budgetmanagementapp.model.service;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRsModel {
    String serviceId;
    Object title;
    Object text;
    String icon;
}
