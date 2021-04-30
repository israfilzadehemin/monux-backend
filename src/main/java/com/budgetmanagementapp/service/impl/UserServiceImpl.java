package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_BODY;
import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_SUBJECT;
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
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.exception.UserRoleNotFoundException;
import com.budgetmanagementapp.exception.UsernameNotUniqueException;
import com.budgetmanagementapp.model.CreatePasswordRqModel;
import com.budgetmanagementapp.model.CreatePasswordRsModel;
import com.budgetmanagementapp.model.SignupRqModel;
import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.model.UserRsModel;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.MailSenderService;
import com.budgetmanagementapp.utility.SmsSenderService;
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
    private final SmsSenderService smsSenderService;
    private final OtpRepository otpRepo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Optional<UserAuthModel> findAuthModelByUsername(String username) {
        return userRepo.findByUsernameAndStatus(username, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepo.findByIdAndStatus(id, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));
    }

    @Override
    @Transactional
    public UserRsModel signup(SignupRqModel username) throws MessagingException {
        if (username.getUsername().contains("@")) {
            CustomValidator.validateEmailFormat(username.getUsername());
        } else {
            CustomValidator.validatePhoneNumberFormat(username.getUsername());
        }

        checkUsernameUniqueness(username.getUsername());
        User user = buildUser(username.getUsername());
        String otp = generateOtp();
        buildOtp(otp, user);

        if (username.getUsername().contains("@")) {
            mailSenderService
                    .sendOtp(username.getUsername(), OTP_CONFIRMATION_SUBJECT, format(OTP_CONFIRMATION_BODY, otp));
        } else {
            smsSenderService
                    .sendOtp(username.getUsername(), OTP_CONFIRMATION_SUBJECT, format(OTP_CONFIRMATION_BODY, otp));
        }

        log.info(format(USER_ADDED_MSG, username.getUsername()));
        return buildUserResponseModel(user);
    }

    @Override
    public CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordModel) {
        User user = userByUsernameAndStatus(passwordModel);
        updatePasswordAndStatusValues(passwordModel.getPassword(), user);

        log.info(format(PASSWORD_CREATED_MSG, user.getUsername()));
        return buildPasswordResponseModel(passwordModel);
    }

    private User buildUser(String username) {
        return userRepo.save(User.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .dateTime(LocalDateTime.now())
                .status(STATUS_PROCESSING)
                .paymentStatus(STATUS_NOT_PAID)
                .roles(Collections.singletonList(
                        roleRepo.findByName(ROLE_USER)
                                .orElseThrow(() -> new UserRoleNotFoundException(ROLE_NOT_FOUND_MSG))))
                .build());
    }

    private void buildOtp(String otp, User user) {
        otpRepo.save(Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .dateTime(LocalDateTime.now())
                .user(user)
                .build());
    }

    private UserRsModel buildUserResponseModel(User user) {
        return UserRsModel.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .creationDateTime(user.getDateTime())
                .status(user.getStatus())
                .paymentStatus(user.getPaymentStatus())
                .build();
    }

    private CreatePasswordRsModel buildPasswordResponseModel(CreatePasswordRqModel passwordModel) {
        return CreatePasswordRsModel.builder()
                .username(passwordModel.getUsername())
                .password(passwordModel.getPassword())
                .build();
    }

    private User userByUsernameAndStatus(CreatePasswordRqModel passwordModel) {
        return userRepo.findByUsernameAndStatus(passwordModel.getUsername(), STATUS_CONFIRMED)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, passwordModel.getUsername())));
    }

    private void updatePasswordAndStatusValues(String password, User user) {
        user.setPassword(encoder.encode(password));
        user.setStatus(STATUS_ACTIVE);
        userRepo.save(user);
    }

    private void checkUsernameUniqueness(String username) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UsernameNotUniqueException(USERNAME_NOT_UNIQUE_MSG);
        }
    }

    private String generateOtp() {
        return Integer.toString(new Random().nextInt(99999 - 10000) + 10000);
    }
}
