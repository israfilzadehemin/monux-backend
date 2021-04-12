package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.OTP_MAIL_BODY;
import static com.budgetmanagementapp.utility.Constant.OTP_MAIL_SUBJECT;
import static com.budgetmanagementapp.utility.Constant.ROLE_USER;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.Constant.STATUS_CONFIRMED;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.Constant.STATUS_NOT_PAID;
import static com.budgetmanagementapp.utility.Constant.STATUS_PROCESSING;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ROLE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USERNAME_NOT_UNIQUE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_ADDED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.exception.UserRoleNotFoundException;
import com.budgetmanagementapp.exception.UsernameNotUniqueException;
import com.budgetmanagementapp.model.CreatePasswordRequestModel;
import com.budgetmanagementapp.model.CreatePasswordResponseModel;
import com.budgetmanagementapp.model.SignupRequestModel;
import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.model.UserResponseModel;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.MailSenderService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final MailSenderService mailSenderService;
    private final OtpRepository otpRepo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Optional<UserAuthModel> findByUsername(String username) {
        return userRepo.findByUsernameAndStatus(username, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepo.findByIdAndStatus(id, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    @Transactional
    public UserResponseModel signupWithEmail(SignupRequestModel signupRequestModel) throws MessagingException {
        CustomValidator.validateEmailFormat(signupRequestModel.getUsername());
        checkEmailUniqueness(signupRequestModel.getUsername());

        User user = userRepo.save(createUser(signupRequestModel.getUsername()));

        String otp = generateOtp();
        otpRepo.save(createOtp(otp, user));

        mailSenderService.sendOtp(
                signupRequestModel.getUsername(),
                OTP_MAIL_SUBJECT,
                String.format(OTP_MAIL_BODY, otp));

        log.info(
                String.format(USER_ADDED_MSG, signupRequestModel.getUsername()));

        return UserResponseModel.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .creationDateTime(user.getCreationDateTime())
                .status(user.getStatus())
                .paymentStatus(user.getPaymentStatus())
                .build();

    }

    @Override
    public CreatePasswordResponseModel createPassword(CreatePasswordRequestModel passwordModel) {
        CustomValidator.validatePassword(passwordModel.getPassword(), passwordModel.getConfirmPassword());

        User user = userRepo.findByUsernameAndStatus(passwordModel.getUsername(), STATUS_CONFIRMED)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, passwordModel.getUsername())));

        user.setPassword(encoder.encode(passwordModel.getPassword()));
        user.setStatus(STATUS_ACTIVE);
        userRepo.save(user);

        log.info(String.format(PASSWORD_CREATED_MSG, user.getUsername()));

        return CreatePasswordResponseModel.builder()
                .username(passwordModel.getUsername())
                .password(passwordModel.getPassword())
                .build();
    }


    private void checkEmailUniqueness(String username) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UsernameNotUniqueException(USERNAME_NOT_UNIQUE_MSG);
        }
    }

    private User createUser(String username) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .creationDateTime(LocalDateTime.now())
                .status(STATUS_PROCESSING)
                .paymentStatus(STATUS_NOT_PAID)
                .roles(Collections.singletonList(
                        roleRepo.findByName(ROLE_USER)
                                .orElseThrow(
                                        () -> new UserRoleNotFoundException(ROLE_NOT_FOUND_MSG))))
                .build();
    }

    private Otp createOtp(String otp, User user) {
        return otpRepo.save(Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .creationDateTime(LocalDateTime.now())
                .user(user)
                .build());
    }

    private String generateOtp() {
        return Integer.toString(new Random().nextInt(99999 - 10000) + 10000);
    }
}
