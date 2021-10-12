package com.budgetmanagementapp.model.home;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FagRsModel {
    String fagId;
    String question;
    String answer;
}
