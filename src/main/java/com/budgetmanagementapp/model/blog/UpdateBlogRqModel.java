package com.budgetmanagementapp.model.blog;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBlogRqModel extends BlogRqModel{

    @NotBlank
    String blogId;
}
