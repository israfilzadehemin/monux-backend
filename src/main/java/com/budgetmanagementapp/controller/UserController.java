package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.USERS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_CREATE_INITIAL_ACCOUNT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_CREATE_PASSWORD_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_FORGET_PASSWORD_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_OTP_CONFIRM_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_RESET_PASSWORD_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_SIGNUP_URL;
import static com.budgetmanagementapp.utility.UrlConstant.USER_UPDATE_LANG_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.user.ConfirmOtpRqModel;
import com.budgetmanagementapp.model.user.ConfirmOtpRsModel;
import com.budgetmanagementapp.model.user.CreatePasswordRqModel;
import com.budgetmanagementapp.model.user.CreatePasswordRsModel;
import com.budgetmanagementapp.model.user.ResetPasswordRqModel;
import com.budgetmanagementapp.model.user.ResetPasswordRsModel;
import com.budgetmanagementapp.model.user.UserInfoRsModel;
import com.budgetmanagementapp.model.user.UserLoginModel;
import com.budgetmanagementapp.model.user.UserRqModel;
import com.budgetmanagementapp.model.user.UserRsModel;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.OtpService;
import com.budgetmanagementapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.mail.MessagingException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "User")
@RequestMapping(USERS_URL)
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final OtpService otpService;

    @ApiOperation("Create user with email")
    @PostMapping(USER_SIGNUP_URL)
    public ResponseEntity<ResponseModel<UserRsModel>> signupWithEmail(@RequestBody @Valid UserRqModel requestBody)
            throws MessagingException {

        log.info(REQUEST_MSG, USERS_URL + USER_SIGNUP_URL, requestBody);
        var response = ResponseModel.of(userService.signup(requestBody), CREATED);

        log.info(RESPONSE_MSG, USERS_URL + USER_SIGNUP_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Confirm otp of user")
    @PostMapping(USER_OTP_CONFIRM_URL)
    public ResponseEntity<ResponseModel<ConfirmOtpRsModel>> confirmOtp(
            @RequestBody @Valid ConfirmOtpRqModel requestBody) {

        log.info(REQUEST_MSG, USERS_URL + USER_OTP_CONFIRM_URL, requestBody);
        var response = ResponseModel.of(otpService.confirmOtp(requestBody), OK);

        log.info(RESPONSE_MSG, USERS_URL + USER_OTP_CONFIRM_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create password for user")
    @PostMapping(USER_CREATE_PASSWORD_URL)
    public ResponseEntity<ResponseModel<CreatePasswordRsModel>> createPassword(
            @RequestBody @Valid CreatePasswordRqModel requestBody) {

        log.info(REQUEST_MSG, USERS_URL + USER_CREATE_PASSWORD_URL, requestBody);
        var response = ResponseModel.of(userService.createPassword(requestBody), CREATED);

        log.info(RESPONSE_MSG, USERS_URL + USER_CREATE_PASSWORD_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Forgot password of user")
    @PostMapping(USER_FORGET_PASSWORD_URL)
    public ResponseEntity<ResponseModel<UserRsModel>> forgetPassword(
            @ApiParam(name = "username", type = "string", required = true) @RequestParam String username)
            throws MessagingException {

        log.info(REQUEST_MSG, USERS_URL + USER_RESET_PASSWORD_URL, username);
        var response = ResponseModel.of(userService.forgetPassword(username), OK);

        log.info(RESPONSE_MSG, USERS_URL + USER_RESET_PASSWORD_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Reset password of user")
    @PostMapping(USER_RESET_PASSWORD_URL)
    public ResponseEntity<ResponseModel<ResetPasswordRsModel>> resetPassword(
            @RequestBody @Valid ResetPasswordRqModel requestBody, @RequestParam String username) {
        log.info(REQUEST_MSG, USERS_URL + USER_RESET_PASSWORD_URL, requestBody);
        var response = ResponseModel.of(userService.resetPassword(username, requestBody), OK);

        log.info(RESPONSE_MSG, USERS_URL + USER_RESET_PASSWORD_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create initial account for user")
    @PostMapping(USER_CREATE_INITIAL_ACCOUNT_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> createInitialAccount(@RequestBody AccountRqModel requestBody) {
        log.info(REQUEST_MSG, USERS_URL + USER_CREATE_INITIAL_ACCOUNT_URL, requestBody);
        var response = ResponseModel.of(accountService.createAccount(requestBody, true), CREATED);

        log.info(RESPONSE_MSG, USERS_URL + USER_CREATE_INITIAL_ACCOUNT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get user information")
    @GetMapping
    public ResponseEntity<ResponseModel<UserInfoRsModel>> getUserInfo(@ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, USERS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(userService.getUserInfo(((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, USERS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update user information")
    @PutMapping
    public ResponseEntity<ResponseModel<UserInfoRsModel>> updateUserInfo(
            Authentication auth, @RequestBody UserRqModel request) {
        log.info(REQUEST_MSG, USERS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(userService.updateUserInfo(
                ((UserDetails) auth.getPrincipal()).getUsername(), request), OK);

        log.info(RESPONSE_MSG, USERS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update user language")
    @PutMapping(USER_UPDATE_LANG_URL)
    public ResponseEntity<ResponseModel<UserInfoRsModel>> updateUserLanguage(
            @ApiParam(name = "language", type = "string", example = "az, en, ru", required = true)
            @RequestParam("language") String language, Authentication auth) {

        log.info(REQUEST_MSG, USERS_URL + USER_UPDATE_LANG_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                userService.updateUserLanguage(((UserDetails) auth.getPrincipal()).getUsername(), language),
                OK);

        log.info(RESPONSE_MSG, USERS_URL + USER_UPDATE_LANG_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete user")
    @DeleteMapping
    public ResponseEntity<ResponseModel<UserRsModel>> updateUserStatus(@ApiIgnore Authentication auth) {
        var response = ResponseModel.of(
                userService.deleteUser(((UserDetails) auth.getPrincipal()).getUsername()), OK);
        log.info(RESPONSE_MSG, USERS_URL, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginModel request) {
        return ResponseEntity.ok().body(new Object());
    }
}
