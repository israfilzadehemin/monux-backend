package com.budgetmanagementapp.model.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRqModel {
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
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string", required = true)
    @NotBlank
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string", required = true)
    @NotBlank
    String textRu;

    @ApiModelProperty(name = "icon", dataType = "string")
    String icon;
}
