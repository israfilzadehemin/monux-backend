package com.budgetmanagementapp.utility;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
public class SmsSenderService {
    public void sendOtp(String username, String subject, String body) {
    }

}
