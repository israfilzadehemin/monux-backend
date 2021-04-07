package com.budgetmanagementapp.model;

import com.budgetmanagementapp.entity.User;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAuthModel {

    private long id;
    private String username;
    private String password;
    private Set<RoleAuthModel> roles = new HashSet<>();

    public UserAuthModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        user.getRoles().forEach(role -> roles.add(new RoleAuthModel(role)));
    }
}
