package com.budgetmanagementapp.model.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateServiceRqModel extends ServiceRqModel{
    @ApiModelProperty(name = "serviceId", dataType = "string", required = true)
    @NotBlank
    String serviceId;
}
