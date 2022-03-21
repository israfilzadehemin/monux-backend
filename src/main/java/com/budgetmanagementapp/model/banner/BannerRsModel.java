package com.budgetmanagementapp.model.banner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerRsModel {
    String bannerId;

    @Schema(example = "Home Page")
    Object title;

    Object text;

    String image;

    @Schema(example = "home")
    String keyword;
}
