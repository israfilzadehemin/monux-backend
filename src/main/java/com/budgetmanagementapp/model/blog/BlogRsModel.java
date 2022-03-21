package com.budgetmanagementapp.model.blog;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "2021-10-31 12:00", required = true)
    LocalDateTime creationDate;

    @Schema(example = "2021-10-31 12:00")
    LocalDateTime updateDate;

    Object title;

    Object text;

    @Schema(required = true)
    String image;
}
