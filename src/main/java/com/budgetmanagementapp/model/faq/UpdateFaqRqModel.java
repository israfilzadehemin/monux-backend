package com.budgetmanagementapp.model.faq;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFaqRqModel extends FaqRqModel{
    String faqId;
}
