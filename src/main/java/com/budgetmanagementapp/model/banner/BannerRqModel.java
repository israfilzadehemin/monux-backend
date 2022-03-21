package com.budgetmanagementapp.model.banner;

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
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerRqModel {
    @Schema(example = "Ana Səhifə", required = true)
    @NotBlank
    String titleAz;

    @Schema(example = "Home Page", required = true)
    @NotBlank
    String titleEn;

    @Schema(example = "Домашняя страница", required = true)
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

    String image;

    @Schema(example = "home", required = true)
    @NotBlank
    String keyword;
}
