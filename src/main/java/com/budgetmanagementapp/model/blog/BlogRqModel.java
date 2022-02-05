package com.budgetmanagementapp.model.blog;

import io.swagger.annotations.ApiModelProperty;
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
public class BlogRqModel {
    @ApiModelProperty(name = "titleAz", dataType = "string", example = "Yeni blog", required = true)
    @NotBlank
    String titleAz;

    @ApiModelProperty(name = "titleEn", dataType = "string", example = "New Blog", required = true)
    @NotBlank
    String titleEn;

    @ApiModelProperty(name = "titleRu", dataType = "string", example = "Новый блог", required = true)
    @NotBlank
    String titleRu;

    @ApiModelProperty(name = "textAz", dataType = "string", required = true)
    @NotBlank
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string", required = true)
    @NotBlank
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string", required = true)
    @NotBlank
    String textRu;

    @ApiModelProperty(name = "image", dataType = "string", required = true)
    @NotBlank
    String image;

    @ApiModelProperty(name = "creationDate", dataType = "string", example = "2021-10-31 12:00", required = true)
    @NotBlank
    String creationDate;

    @ApiModelProperty(name = "updateDate", dataType = "string", example = "2021-10-31 12:00")
    String updateDate;
}
