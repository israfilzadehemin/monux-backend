package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.utility.PaymentStatus;
import com.budgetmanagementapp.utility.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static com.budgetmanagementapp.utility.Constant.ROLE_USER;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.MsgConstant.ROLE_NOT_FOUND_MSG;

@AllArgsConstructor
@Component
public class UserBuilder {
    private final RoleRepository roleRepo;

    public User buildUser(String username, String fullName) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .fullName(fullName)
                .dateTime(LocalDateTime.now())
                .status(UserStatus.PROCESSING)
                .paymentStatus(PaymentStatus.NOT_PAID)
                .roles(Collections.singletonList(
                        roleRepo.byName(ROLE_USER)
                                .orElseThrow(() -> new DataNotFoundException(ROLE_NOT_FOUND_MSG, 1009))))
                .build();
    }

    public Otp buildOtp(String otp, User user) {
        return Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .dateTime(LocalDateTime.now())
                .user(user)
                .build();
    }
}
