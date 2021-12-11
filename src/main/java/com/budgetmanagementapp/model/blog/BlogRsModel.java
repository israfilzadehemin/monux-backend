package com.budgetmanagementapp.model.blog;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(
            name = "blogId",
            dataType = "string")
    String blogId;

    @ApiModelProperty(
            name = "creationDate",
            dataType = "localDateTime",
            example = "2021-10-31 12:00",
            required = true)
    LocalDateTime creationDate;

    @ApiModelProperty(
            name = "updateDate",
            dataType = "localDateTime",
            example = "2021-10-31 12:00")
    LocalDateTime updateDate;

    @ApiModelProperty(
            name = "titleAz",
            dataType = "string")
    String titleAz;

    @ApiModelProperty(
            name = "titleEn",
            dataType = "string")
    String titleEn;

    @ApiModelProperty(
            name = "titleRu",
            dataType = "string")
    String titleRu;

    @ApiModelProperty(
            name = "textAz",
            dataType = "string")
    String textAz;

    @ApiModelProperty(
            name = "textEn",
            dataType = "string")
    String textEn;

    @ApiModelProperty(
            name = "textRu",
            dataType = "string")
    String textRu;

    @ApiModelProperty(
            name = "image",
            dataType = "string",
            required = true)
    String image;
}
