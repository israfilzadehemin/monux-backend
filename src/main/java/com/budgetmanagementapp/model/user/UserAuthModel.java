package com.budgetmanagementapp.model.user;

import com.budgetmanagementapp.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthModel {

    long id;
    String username;
    String password;
    Set<RoleAuthModel> roles = new HashSet<>();

    public UserAuthModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        user.getRoles().forEach(role -> roles.add(new RoleAuthModel(role)));
    }
}
