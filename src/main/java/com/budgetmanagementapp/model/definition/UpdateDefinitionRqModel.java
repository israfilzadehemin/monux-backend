package com.budgetmanagementapp.model.definition;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDefinitionRqModel extends DefinitionRqModel {
    @ApiModelProperty(
            name = "definitionId",
            dataType = "string",
            example = "")
    String definitionId;
}
