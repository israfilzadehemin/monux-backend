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
    @ApiModelProperty(
            name = "title",
            dataType = "string",
            example = "New Blog",
            required = true)
    @NotBlank
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string",
            required = true)
    @NotBlank
    String text;

    @ApiModelProperty(
            name = "image",
            dataType = "string",
            required = true)
    @NotBlank
    String image;

    @ApiModelProperty(
            name = "creationDate",
            dataType = "string",
            example = "2021-10-31 12:00",
            required = true)
    @NotBlank
    String creationDate;

    @ApiModelProperty(
            name = "updateDate",
            dataType = "string",
            example = "2021-10-31 12:00")
    String updateDate;
}
