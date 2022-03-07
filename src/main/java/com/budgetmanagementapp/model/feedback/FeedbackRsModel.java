package com.budgetmanagementapp.model.feedback;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRsModel {
    String feedbackId;
    String description;
    LocalDateTime creationDateTime;
    String status;
}
