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
public class UpdateBlogRqModel extends BlogRqModel{

    @ApiModelProperty(
            name = "blogId",
            dataType = "string",
            example = "")
    @NotBlank
    String blogId;
}
