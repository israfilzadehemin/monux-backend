package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.UserRoleNotFoundException;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static com.budgetmanagementapp.utility.Constant.*;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.MsgConstant.ROLE_NOT_FOUND_MSG;

@AllArgsConstructor
@Component
public class UserBuilder {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final OtpRepository otpRepo;

    public User buildUser(String username, String fullName) {
        return userRepo.save(User.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .fullName(fullName)
                .dateTime(LocalDateTime.now())
                .status(STATUS_PROCESSING)
                .paymentStatus(STATUS_NOT_PAID)
                .roles(Collections.singletonList(
                        roleRepo.byName(ROLE_USER)
                                .orElseThrow(() -> new UserRoleNotFoundException(ROLE_NOT_FOUND_MSG))))
                .build());
    }

    public void buildOtp(String otp, User user) {
        otpRepo.save(Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .dateTime(LocalDateTime.now())
                .user(user)
                .build());
    }
}
