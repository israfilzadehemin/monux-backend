package com.budgetmanagementapp.model.banner;

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
public class UpdateBannerRqModel extends BannerRqModel {
    @ApiModelProperty(name = "bannerId", dataType = "string", required = true)
    @NotBlank
    String bannerId;
}
