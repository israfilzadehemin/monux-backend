package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.Constant.USER_CONTROLLER_URL;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(USER_CONTROLLER_URL)
@AllArgsConstructor
public class UserController {

    @GetMapping("/demo")
    public ResponseEntity<?> demo() {
        return ResponseEntity.ok("Hey");
    }
}
