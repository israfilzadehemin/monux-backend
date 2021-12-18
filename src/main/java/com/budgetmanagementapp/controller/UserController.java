package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.user.*;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.OtpService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.MailSenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "User")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final MailSenderService mailSenderService;
    private final OtpService otpService;

    @ApiOperation("Create user with email")
    @PostMapping(USER_SIGNUP_URL)
    public ResponseEntity<ResponseModel<UserRsModel>> signupWithEmail(@RequestBody @Valid SignupRqModel requestBody)
            throws MessagingException {

        log.info(String.format(REQUEST_MSG, USER_SIGNUP_URL, requestBody));
        return ResponseEntity.ok(ResponseModel.of(
                userService.signup(requestBody), HttpStatus.CREATED));
    }

    @ApiOperation("Confirm otp of user")
    @PostMapping(USER_OTP_CONFIRM_URL)
    public ResponseEntity<ResponseModel<ConfirmOtpRsModel>> confirmOtp(@RequestBody @Valid ConfirmOtpRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_OTP_CONFIRM_URL, requestBody));
        return ResponseEntity.ok(ResponseModel.of(
                otpService.confirmOtp(requestBody), HttpStatus.OK));
    }

    @ApiOperation("Create password for user")
    @PostMapping(USER_CREATE_PASSWORD_URL)
    public ResponseEntity<ResponseModel<CreatePasswordRsModel>> createPassword(@RequestBody @Valid CreatePasswordRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_PASSWORD_URL, requestBody));
        return ResponseEntity.ok(ResponseModel.of(
                userService.createPassword(requestBody), HttpStatus.CREATED));
    }

    @ApiOperation("Forgot password of user")
    @PostMapping(USER_FORGET_PASSWORD_URL)
    public ResponseEntity<ResponseModel<UserRsModel>> forgetPassword(
            @ApiParam(
                    name = "username",
                    type = "string",
                    required = true)
            @RequestParam String username) throws MessagingException {
        log.info(USER_RESET_PASSWORD_URL);
        return ResponseEntity.ok(ResponseModel.of(
                userService.forgetPassword(username), HttpStatus.OK));
    }

    @ApiOperation("Reset password of user")
    @PostMapping(USER_RESET_PASSWORD_URL)
    public ResponseEntity<ResponseModel<ResetPasswordRsModel>> resetPassword(@RequestBody @Valid ResetPasswordRqModel requestBody,
                                                                             @RequestParam String username) {
        log.info(String.format(REQUEST_MSG, USER_RESET_PASSWORD_URL, requestBody));
        return ResponseEntity.ok(ResponseModel.of(
                userService.resetPassword(username, requestBody), HttpStatus.OK));
    }

    @ApiOperation("Create initial account for user")
    @PostMapping(USER_CREATE_INITIAL_ACCOUNT_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> createInitialAccount(@RequestBody AccountRqModel requestBody) {
        log.info(String.format(REQUEST_MSG, USER_CREATE_INITIAL_ACCOUNT_URL, requestBody));
        return ResponseEntity.ok(ResponseModel.of(
                accountService.createAccount(requestBody, true), HttpStatus.CREATED));
    }

    @ApiOperation("Demo for email sending")
    @GetMapping("/demo")
    public ResponseEntity<String> demo() throws MessagingException {
        mailSenderService.sendEmail("israfilzadehemin@gmail.com", "Hey", "Hello");
        return ResponseEntity.ok("Hey");
    }

    @ApiOperation("Get user information")
    @GetMapping(USER_INFO_URL)
    public ResponseEntity<ResponseModel<UserInfoRsModel>> getUserInfo(Authentication auth) {
        log.info(format(REQUEST_MSG, USER_INFO_URL, NO_BODY_MSG));
        return ResponseEntity.ok(ResponseModel.of(
                userService.userInfo(((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update user language")
    @PutMapping(USER_UPDATE_LANG_URL)
    public ResponseEntity<ResponseModel<UserInfoRsModel>> updateUserLanguage(
            Authentication auth,
            @ApiParam(name = "language",
                    type = "string",
                    example = "az, en, ru",
                    required = true)
            @RequestParam("language") String language) {
        log.info(format(REQUEST_MSG, USER_INFO_URL, NO_BODY_MSG));
        return ResponseEntity.ok(ResponseModel.of(
                userService.updateUserLanguage(((UserDetails) auth.getPrincipal()).getUsername(), language), HttpStatus.OK));
    }
}
