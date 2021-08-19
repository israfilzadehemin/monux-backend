package com.budgetmanagementapp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteTemplateRqModel {

    List<String> templateIds;
}
