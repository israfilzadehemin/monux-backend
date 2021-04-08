package com.budgetmanagementapp.handler;

import com.budgetmanagementapp.exception.InvalidTokenGenerationException;
import com.budgetmanagementapp.model.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidTokenGenerationException.class)
    public ResponseEntity<?> handlerInvalidTokenGeneration(InvalidTokenGenerationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Token generation failed"));
    }
}
