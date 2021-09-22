package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.user.ConfirmOtpRqModel;
import com.budgetmanagementapp.model.user.CreatePasswordRqModel;
import com.budgetmanagementapp.model.user.ResetPasswordRqModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.user.SignupRqModel;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.OtpService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.MailSenderService;
import javax.mail.MessagingException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final MailSenderService mailSenderService;
    private final OtpService otpService;

    @PostMapping(USER_SIGNUP_URL)
    public ResponseEntity<?> signupWithEmail(@RequestBody @Valid SignupRqModel requestBody)
            throws MessagingException {

        log.info(String.format(REQUEST_MSG, USER_SIGNUP_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(userService.signup(requestBody))
                        .build()
        );
    }

    @PostMapping(USER_OTP_CONFIRM_URL)
    public ResponseEntity<?> confirmOtp(@RequestBody @Valid ConfirmOtpRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_OTP_CONFIRM_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(otpService.confirmOtp(requestBody))
                        .build()
        );
    }

    @PostMapping(USER_CREATE_PASSWORD_URL)
    public ResponseEntity<?> createPassword(@RequestBody @Valid CreatePasswordRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_PASSWORD_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(userService.createPassword(requestBody))
                        .build()
        );
    }

    @PostMapping(USER_FORGET_PASSWORD_URL)
    public ResponseEntity<?> forgetPassword(@RequestParam String username) throws MessagingException {
        log.info(USER_RESET_PASSWORD_URL);
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(userService.forgetPassword(username))
                        .build()
        );
    }

    @PostMapping(USER_RESET_PASSWORD_URL)
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRqModel requestBody,
                                           @RequestParam String username) {
        log.info(String.format(REQUEST_MSG, USER_RESET_PASSWORD_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(userService.resetPassword(username, requestBody))
                        .build()
        );
    }

    @PostMapping(USER_CREATE_INITIAL_ACCOUNT_URL)
    public ResponseEntity<?> createInitialAccount(@RequestBody AccountRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_INITIAL_ACCOUNT_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(accountService.createAccount(requestBody, true))
                        .build()
        );
    }

    @GetMapping("/demo")
    public ResponseEntity<?> demo() throws MessagingException {
        mailSenderService.sendEmail("israfilzadehemin@gmail.com", "Hey", "Hello");
        return ResponseEntity.ok("Hey");
    }

    @GetMapping(USER_INFO_URL)
    public ResponseEntity<?> getUserInfo(Authentication auth) {
        log.info(format(REQUEST_MSG, USER_INFO_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(userService.userInfo(((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

}
