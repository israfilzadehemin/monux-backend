package com.budgetmanagementapp.configuration;

import com.budgetmanagementapp.exception.InvalidTokenGenerationException;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.UserLoginModel;
import com.budgetmanagementapp.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginModel loginModel) {
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(
                                authService.login(loginModel.getUsername(), loginModel.getPassword(),
                                        loginModel.getRememberMe())
                                        .orElseThrow(InvalidTokenGenerationException::new))
                        .build());
    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello!";
    }
}
