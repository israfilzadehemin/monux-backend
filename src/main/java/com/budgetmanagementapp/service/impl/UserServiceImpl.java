package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.mapper.UserMapper.USER_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_BODY;
import static com.budgetmanagementapp.utility.Constant.OTP_CONFIRMATION_SUBJECT;
import static com.budgetmanagementapp.utility.Constant.RESET_PASSWORD_BODY;
import static com.budgetmanagementapp.utility.Constant.RESET_PASSWORD_SUBJECT;
import static com.budgetmanagementapp.utility.Constant.STATUS_USED;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.UserBuilder;
import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.*;
import com.budgetmanagementapp.model.user.*;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.MailSenderService;
import com.budgetmanagementapp.utility.SmsSenderService;
import java.util.Optional;
import java.util.Random;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.budgetmanagementapp.utility.UserStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final OtpRepository otpRepo;
    private final MailSenderService mailSenderService;
    private final SmsSenderService smsSenderService;
    private final BCryptPasswordEncoder encoder;
    private final UserBuilder userBuilder;

    @Override
    public Optional<UserAuthModel> findAuthModelByUsername(String username) {
        return userRepo.byUsernameAndStatus(username, UserStatus.ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepo.byIdAndStatus(id, UserStatus.ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public User findByUsername(String username) {
        var user = userRepo
                .byUsernameAndStatus(username, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));

        log.info(USER_BY_USERNAME, username, user);
        return user;
    }

    @Override
    @Transactional
    public UserRsModel signup(UserRqModel signupRqModel) throws MessagingException {
        CustomValidator.validateUsername(signupRqModel.getUsername());
        CustomValidator.validateFullName(signupRqModel.getFullName());

        checkUsernameUniqueness(signupRqModel.getUsername());
        User user = userRepo.save(
                userBuilder.buildUser(signupRqModel.getUsername(), signupRqModel.getFullName()));
        String otp = generateOtp();
        otpRepo.save(userBuilder.buildOtp(otp, user));

        if (signupRqModel.getUsername().contains("@")) {
            mailSenderService
                    .sendEmail(signupRqModel.getUsername(), OTP_CONFIRMATION_SUBJECT,
                            format(OTP_CONFIRMATION_BODY, otp));
        } else {
            smsSenderService
                    .sendMessage(signupRqModel.getUsername(), OTP_CONFIRMATION_SUBJECT,
                            format(OTP_CONFIRMATION_BODY, otp));
        }

        log.info(USER_ADDED_MSG, signupRqModel.getUsername());
        return USER_MAPPER_INSTANCE.buildUserResponseModel(user);
    }

    @Override
    public CreatePasswordRsModel createPassword(CreatePasswordRqModel requestBody) {
        User user = userByUsernameAndStatus(requestBody);
        updatePasswordAndStatusValues(requestBody.getPassword(), user);
        log.info(PASSWORD_CREATED_MSG, user.getUsername());
        return USER_MAPPER_INSTANCE.buildPasswordResponseModel(requestBody);
    }

    @Override
    public UserRsModel forgetPassword(String username) throws MessagingException {
        CustomValidator.validateUsername(username);

        String otp = generateOtp();
        otpRepo.save(userBuilder.buildOtp(otp, findByUsername(username)));

        if (username.contains("@")) {
            mailSenderService.sendEmail(username, RESET_PASSWORD_SUBJECT, format(RESET_PASSWORD_BODY, otp));
        } else {
            smsSenderService.sendMessage(username, RESET_PASSWORD_SUBJECT, format(OTP_CONFIRMATION_BODY, otp));
        }
        return USER_MAPPER_INSTANCE.buildUserResponseModel(findByUsername(username));
    }

    @Override
    @Transactional
    public ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody) {
        User user = findByUsername(username);
        Otp otp = otpRepo.findByOtp(requestBody.getOtp()).orElseThrow(() -> new InvalidOtpException(INVALID_OTP_MSG));
        CustomValidator.checkOtpAvailability(otp);

        checkPasswordEquality(requestBody.getPassword(), requestBody.getConfirmPassword());
        updatePassword(requestBody.getPassword(), user);

        otp.setStatus(STATUS_USED);
        otpRepo.save(otp);

        log.info(PASSWORD_UPDATED_MSG, user.getUsername());
        return USER_MAPPER_INSTANCE.buildResetPasswordResponseModel(user.getUsername(), requestBody);
    }

    @Override
    public UserInfoRsModel getUserInfo(String username) {
        User user = userRepo.byUsername(username)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));

        UserInfoRsModel userInfoRsModel = USER_MAPPER_INSTANCE.buildUserInfoResponseModel(user);

        log.info(USER_BY_USERNAME, username, userInfoRsModel);
        return userInfoRsModel;
    }

    @Override
    public UserInfoRsModel updateUserInfo(String username, UserRqModel userRqModel) {
        CustomValidator.validateUsername(userRqModel.getUsername());
        CustomValidator.validateFullName(userRqModel.getFullName());

        User user = findByUsername(username);
        user.setUsername(userRqModel.getUsername());
        user.setFullName(userRqModel.getFullName());

        userRepo.save(user);
        var userInfoRsModel = USER_MAPPER_INSTANCE.buildUserInfoResponseModel(user);

        log.info(USER_UPDATE_INFO_MSG, userInfoRsModel);
        return userInfoRsModel;
    }

    @Override
    public UserInfoRsModel updateUserLanguage(String username, String language) {
        User user = userRepo.byUsername(username)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));
        user.setLanguage(language);
        userRepo.save(user);

        UserInfoRsModel userInfoRsModel = USER_MAPPER_INSTANCE.buildUserInfoResponseModel(user);

        log.info(USER_UPDATE_LANG_MSG, userInfoRsModel);
        return userInfoRsModel;
    }

    @Override
    public UserRsModel deleteUser(String username) {
        var user = findByUsername(username);

        user.setStatus(UserStatus.INACTIVE);
        userRepo.save(user);

        var response = USER_MAPPER_INSTANCE.buildUserResponseModel(user);
        log.info(USER_DEACTIVATE_MSG, response);
        return response;
    }

    private User userByUsernameAndStatus(CreatePasswordRqModel requestBody) {
        User user = userRepo.byUsernameAndStatus(requestBody.getUsername(), UserStatus.CONFIRMED)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, requestBody.getUsername())));

        log.info(USER_BY_USERNAME_STATUS, requestBody.getUsername(), UserStatus.CONFIRMED, user.getUserId());
        return user;
    }

    private void updatePasswordAndStatusValues(String password, User user) {
        user.setPassword(encoder.encode(password));
        user.setStatus(UserStatus.ACTIVE);
        userRepo.save(user);
    }

    private void updatePassword(String password, User user) {
        checkPasswordDuplication(user.getPassword(), password);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
    }

    private void checkPasswordDuplication(String oldPassword, String newPassword) {
        if (encoder.matches(newPassword, oldPassword)) {
            throw new DuplicatePasswordException(PASSWORD_DUPLICATION_MSG);
        }
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
