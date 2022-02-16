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
    @ApiModelProperty(name = "blogId", dataType = "string")
    String blogId;

    @ApiModelProperty(name = "creationDate", dataType = "localDateTime", example = "2021-10-31 12:00", required = true)
    LocalDateTime creationDate;

    @ApiModelProperty(name = "updateDate", dataType = "localDateTime", example = "2021-10-31 12:00")
    LocalDateTime updateDate;

    @ApiModelProperty(name = "title", dataType = "string")
    Object title;

    @ApiModelProperty(name = "text", dataType = "string")
    Object text;

    @ApiModelProperty(name = "image", dataType = "string", required = true)
    String image;
}
