package com.budgetmanagementapp.model.banner;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "titleAz", dataType = "string", example = "Ana Səhifə", required = true)
    @NotBlank
    String titleAz;

    @ApiModelProperty(name = "titleEn", dataType = "string", example = "Home Page", required = true)
    @NotBlank
    String titleEn;

    @ApiModelProperty(name = "titleRu", dataType = "string", example = "Домашняя страница", required = true)
    @NotBlank
    String titleRu;

    @ApiModelProperty(name = "textAz", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textRu;

    @ApiModelProperty(name = "image", dataType = "string")
    String image;

    @ApiModelProperty(name = "keyword", dataType = "string", example = "home", required = true)
    @NotBlank
    String keyword;
}
