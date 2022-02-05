package com.budgetmanagementapp.security;

import static com.budgetmanagementapp.utility.Constant.CONTENT_TYPE_JSON;
import static com.budgetmanagementapp.utility.Constant.HEADER_REMEMBER_ME;
import static com.budgetmanagementapp.utility.Constant.JWT_TOKEN_FORMAT;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_CREDENTIALS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.JWT_TOKEN_GENERATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.USER_LOGIN_URL;

import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.model.ErrorResponseModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.user.UserAuthModel;
import com.budgetmanagementapp.model.user.UserLoginModel;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final JwtService jwtService;
    private final ObjectMapper om = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager authManager,
                                UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        super.setAuthenticationManager(authManager);
        super.setFilterProcessesUrl(USER_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            UserLoginModel login = om.readValue(req.getInputStream(), UserLoginModel.class);
            res.addHeader(HEADER_REMEMBER_ME, login.getRememberMe().toString());

            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    login.getUsername(), login.getPassword()));
        } catch (IOException e) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication authResult) throws IOException {

        String username = ((UserDetails) authResult.getPrincipal()).getUsername();

        UserAuthModel userAuthModel = userService.findAuthModelByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));

        String token = jwtService
                .generateToken(userAuthModel.getUsername(), Boolean.parseBoolean(res.getHeader(HEADER_REMEMBER_ME)));

        generateResponse(res, HttpStatus.OK, String.format(JWT_TOKEN_FORMAT, Constant.JWT_PREFIX, token));
        logger.info(String.format(JWT_TOKEN_GENERATED_MSG, userAuthModel.getUsername()));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        generateResponse(response, HttpStatus.BAD_REQUEST, ErrorResponseModel.builder()
                .code(1010)
                .message(INVALID_CREDENTIALS_MSG)
                .build() );
        logger.info(INVALID_CREDENTIALS_MSG);
    }


    private void generateResponse(HttpServletResponse res, HttpStatus status, Object body) throws IOException {
        res.setStatus(status.value());
        res.setContentType(CONTENT_TYPE_JSON);

        om.writeValue(
                res.getOutputStream(),
                ResponseModel.builder()
                        .status(status)
                        .body(body)
                        .build());
    }
}
