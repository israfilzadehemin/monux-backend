package com.budgetmanagementapp.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoRsModel {
    @ApiModelProperty(name = "username", dataType = "string")
    String username;

    @ApiModelProperty(name = "fullName", dataType = "string")
    String fullName;

    @ApiModelProperty(name = "language", dataType = "string")
    String language;
}
