package com.budgetmanagementapp.model.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRqModel {
    @Schema(example = "Yeni blog", required = true)
    @NotBlank
    String titleAz;

    @Schema(example = "New Blog", required = true)
    @NotBlank
    String titleEn;

    @Schema(example = "Новый блог", required = true)
    @NotBlank
    String titleRu;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textAz;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textEn;

    @Schema(required = true)
    @NotBlank
    @Size(max = 5000)
    String textRu;

    @Schema(required = true)
    @NotBlank
    String image;

    @Schema(example = "2021-10-31 12:00", required = true)
    @NotBlank
    String creationDate;

    @Schema(example = "2021-10-31 12:00")
    String updateDate;
}
