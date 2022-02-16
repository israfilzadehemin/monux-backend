package com.budgetmanagementapp.model.plan;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanRqModel {
    @ApiModelProperty(name = "titleAz", dataType = "string", required = true)
    @NotBlank
    String titleAz;

    @ApiModelProperty(name = "titleEn", dataType = "string", required = true)
    @NotBlank
    String titleEn;

    @ApiModelProperty(name = "titleRu", dataType = "string", required = true)
    @NotBlank
    String titleRu;

    @ApiModelProperty(name = "textAz", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textAz;

    @ApiModelProperty(name = "textEn", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textEn;

    @ApiModelProperty(name = "textRu", dataType = "string", required = true)
    @NotBlank
    @Size(max = 5000)
    String textRu;

    @ApiModelProperty(name = "price", dataType = "bigDecimal", example = "30", required = true)
    BigDecimal price;

    @ApiModelProperty(name = "periodType", dataType = "string", example = "MONTHLY", required = true)
    @NotBlank
    String periodType;

    @ApiModelProperty(name = "periodType", dataType = "list of string")
    List<String> featuresIds;
}
