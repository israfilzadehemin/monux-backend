package com.budgetmanagementapp.model.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerRsModel {
    @ApiModelProperty(
            name = "bannerId",
            dataType = "string")
    String bannerId;

    @ApiModelProperty(
            name = "title",
            dataType = "string",
            example = "Home Page")
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    String text;

    @ApiModelProperty(
            name = "image",
            dataType = "string")
    String image;

    @ApiModelProperty(
            name = "keyword",
            dataType = "string",
            example = "home")
    String keyword;
}
