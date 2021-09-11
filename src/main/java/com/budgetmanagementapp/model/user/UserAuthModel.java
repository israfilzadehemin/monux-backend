package com.budgetmanagementapp.model.user;

import com.budgetmanagementapp.entity.User;
import java.util.HashSet;
import java.util.Set;

import com.budgetmanagementapp.model.user.RoleAuthModel;
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
