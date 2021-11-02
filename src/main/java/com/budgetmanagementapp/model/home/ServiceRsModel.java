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
public class ServiceRsModel {
    @ApiModelProperty(
            name = "serviceId",
            dataType = "string")
    String serviceId;

    @ApiModelProperty(
            name = "title",
            dataType = "string")
    String title;

    @ApiModelProperty(
            name = "text",
            dataType = "string")
    String text;

    @ApiModelProperty(
            name = "icon",
            dataType = "string")
    String icon;
}
