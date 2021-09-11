package com.budgetmanagementapp.model.user;

import com.budgetmanagementapp.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAuthModel {

    long id;
    String name;

    public RoleAuthModel(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
