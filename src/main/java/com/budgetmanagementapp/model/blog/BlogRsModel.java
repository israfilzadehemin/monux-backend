package com.budgetmanagementapp.model.blog;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRsModel {
    String blogId;
    LocalDateTime creationDate;
    LocalDateTime updateDate;
    String title;
    String text;
    String icon;
}
