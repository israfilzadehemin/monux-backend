package com.budgetmanagementapp.model.faq;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqRsModel {
    String faqId;
    String question;
    String answer;
}
