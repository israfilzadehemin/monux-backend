package com.budgetmanagementapp.model.blog;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRqModel {
    String title;
    String text;
    String image;
    String creationDate;
    String updateDate;
}
