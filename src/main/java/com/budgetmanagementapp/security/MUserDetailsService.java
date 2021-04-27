package com.budgetmanagementapp.security;

import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@AllArgsConstructor
@Log4j2
public class MUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public static UserDetails map(UserAuthModel userAuthModel) {
        return new MUserDetails(userAuthModel.getId(), userAuthModel.getUsername(), userAuthModel.getPassword(),
                userAuthModel.getRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findAuthModelByUsername(username)
                .map(MUserDetailsService::map)
                .orElseThrow(() -> {
                    String message = String.format(USER_NOT_FOUND_MSG, username);
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });

    }

    public UserDetails loadById(long userId) {
        return userService.findById(userId)
                .map(MUserDetailsService::map)
                .orElseThrow(() -> {
                    String message = String.format(USER_NOT_FOUND_MSG, userId);
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });
    }

}
