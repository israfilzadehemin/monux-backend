package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_BODY;
import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_SUBJECT;
import static com.budgetmanagementapp.utility.Constant.RESET_PASSWORD_BODY;
import static com.budgetmanagementapp.utility.Constant.RESET_PASSWORD_SUBJECT;
import static com.budgetmanagementapp.utility.Constant.ROLE_USER;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.Constant.STATUS_CONFIRMED;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.Constant.STATUS_NOT_PAID;
import static com.budgetmanagementapp.utility.Constant.STATUS_PROCESSING;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_EQUALITY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ROLE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USERNAME_NOT_UNIQUE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_ADDED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.USER_FULL_RESET_PASSWORD_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.UserBuilder;
import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.exception.UserRoleNotFoundException;
import com.budgetmanagementapp.exception.UsernameNotUniqueException;
import com.budgetmanagementapp.mapper.UserMapper;
import com.budgetmanagementapp.model.CreatePasswordRqModel;
import com.budgetmanagementapp.model.CreatePasswordRsModel;
import com.budgetmanagementapp.model.ResetPasswordRqModel;
import com.budgetmanagementapp.model.ResetPasswordRsModel;
import com.budgetmanagementapp.model.SignupRqModel;
import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.model.UserRsModel;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.RoleRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.EncryptionTool;
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
    private final MailSenderService mailSenderService;
    private final SmsSenderService smsSenderService;
    private final BCryptPasswordEncoder encoder;
    private final UserBuilder userBuilder;

    @Override
    public Optional<UserAuthModel> findAuthModelByUsername(String username) {
        return userRepo.byUsernameAndStatus(username, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepo.byIdAndStatus(id, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo
                .byUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));
    }

    @Override
    @Transactional
    public UserRsModel signup(SignupRqModel signupRqModel) throws MessagingException {
        if (signupRqModel.getUsername().contains("@")) {
            CustomValidator.validateEmailFormat(signupRqModel.getUsername());
        } else {
            CustomValidator.validatePhoneNumberFormat(signupRqModel.getUsername());
        }

        checkUsernameUniqueness(signupRqModel.getUsername());
        User user = buildUser(signupRqModel.getUsername(), signupRqModel.getFullName());
        String otp = generateOtp();
        buildOtp(otp, user);

        if (signupRqModel.getUsername().contains("@")) {
            mailSenderService
                    .sendEmail(signupRqModel.getUsername(), OTP_CONFIRMATION_SUBJECT, format(OTP_CONFIRMATION_BODY, otp));
        } else {
            smsSenderService
                    .sendMessage(signupRqModel.getUsername(), OTP_CONFIRMATION_SUBJECT, format(OTP_CONFIRMATION_BODY, otp));
        }

        log.info(format(USER_ADDED_MSG, signupRqModel.getUsername()));
        return UserMapper.INSTANCE.buildUserResponseModel(user);
    }

    @Override
    public CreatePasswordRsModel createPassword(CreatePasswordRqModel requestBody) {
        User user = findByUsername(requestBody.getUsername());
        updatePasswordAndStatusValues(requestBody.getPassword(), user);

        log.info(format(PASSWORD_CREATED_MSG, user.getUsername()));
        return UserMapper.INSTANCE.buildPasswordResponseModel(requestBody);
    }

    @Override
    public ResetPasswordRsModel forgetPassword(String username, ResetPasswordRqModel requestBody) throws MessagingException {
        String encryptedUsername = EncryptionTool.encrypt(username);
        if (username.contains("@")) {
            CustomValidator.validateEmailFormat(username);
            mailSenderService
                    .sendEmail(username, RESET_PASSWORD_SUBJECT, format(RESET_PASSWORD_BODY,
                            USER_FULL_RESET_PASSWORD_URL+encryptedUsername));
        } else {
            CustomValidator.validatePhoneNumberFormat(username);
            smsSenderService
                    .sendMessage(username, RESET_PASSWORD_SUBJECT, format(OTP_CONFIRMATION_BODY,
                            USER_FULL_RESET_PASSWORD_URL+encryptedUsername));
        }
        return UserMapper.INSTANCE.buildResetPasswordResponseModel(username, requestBody);
    }

    @Override
    public ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody) {
        User user = findByUsername(EncryptionTool.decrypt(username));
        checkPasswordEquality(requestBody.getPassword(), requestBody.getConfirmPassword());
        updatePassword(requestBody.getPassword(), user);

        log.info(format(PASSWORD_UPDATED_MSG, user.getUsername()));
        return UserMapper.INSTANCE.buildResetPasswordResponseModel(user.getUsername(), requestBody);
    }

    private User buildUser(String username, String fullName) {
        return userBuilder.buildUser(username, fullName);
    }

    private void buildOtp(String otp, User user) {
        userBuilder.buildOtp(otp, user);
    }

    private void updatePasswordAndStatusValues(String password, User user) {
        user.setPassword(encoder.encode(password));
        user.setStatus(STATUS_ACTIVE);
        userRepo.save(user);
    }

    private void updatePassword(String password, User user) {
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
    }

    private void checkUsernameUniqueness(String username) {
        if (userRepo.byUsername(username).isPresent()) {
            throw new UsernameNotUniqueException(USERNAME_NOT_UNIQUE_MSG);
        }
    }

    private void checkPasswordEquality(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException(PASSWORD_EQUALITY_MSG);
        }
    }

    private String generateOtp() {
        return Integer.toString(new Random().nextInt(99999 - 10000) + 10000);
    }
}
