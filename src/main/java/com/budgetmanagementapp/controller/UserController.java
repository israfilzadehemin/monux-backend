package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.USER_CREATE_INITIAL_ACCOUNT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_CREATE_PASSWORD_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_OTP_CONFIRM_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_SIGNUP_WITH_EMAIL_URL;

import com.budgetmanagementapp.model.ConfirmOtpRequestModel;
import com.budgetmanagementapp.model.CreateAccountModel;
import com.budgetmanagementapp.model.CreatePasswordRequestModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.SignupRequestModel;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.OtpService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.MailSenderService;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final MailSenderService mailSenderService;
    private final OtpService otpService;

    @PostMapping(USER_SIGNUP_WITH_EMAIL_URL)
    public ResponseEntity<?> signupWithEmail(@RequestBody SignupRequestModel sigunpRequestModel)
            throws MessagingException {
        log.info(String.format(REQUEST_MSG, USER_SIGNUP_WITH_EMAIL_URL, sigunpRequestModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(userService.signupWithEmail(sigunpRequestModel))
                        .build()
        );
    }

    @PostMapping(USER_OTP_CONFIRM_URL)
    public ResponseEntity<?> confirmOtp(@RequestBody ConfirmOtpRequestModel otpRequestModel) {
        log.info(String.format(REQUEST_MSG, USER_OTP_CONFIRM_URL, otpRequestModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(otpService.confirmOtp(otpRequestModel))
                        .build()
        );
    }

    @PostMapping(USER_CREATE_PASSWORD_URL)
    public ResponseEntity<?> createPassword(@RequestBody CreatePasswordRequestModel passwordRequestModel) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_PASSWORD_URL, passwordRequestModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(userService.createPassword(passwordRequestModel))
                        .build()
        );
    }

    @PostMapping(USER_CREATE_INITIAL_ACCOUNT_URL)
    public ResponseEntity<?> createInitialAccount(@RequestBody CreateAccountModel createAccountModel) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_INITIAL_ACCOUNT_URL, createAccountModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(accountService.createInitialAccount(createAccountModel))
                        .build()
        );
    }

    @GetMapping("/demo")
    public ResponseEntity<?> demo() throws MessagingException {
        mailSenderService.sendOtp("israfilzadehemin@gmail.com", "Hey", "Hello");
        return ResponseEntity.ok("Hey");
    }
}
