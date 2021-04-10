package com.budgetmanagementapp.handler;


import static com.budgetmanagementapp.utility.Constant.UNEXPECTED_EXCEPTION_MSG;

import com.budgetmanagementapp.exception.GenericException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.model.ResponseModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        if (exception instanceof InvalidModelException
                || exception instanceof GenericException
                || exception instanceof MalformedJwtException
                || exception instanceof SignatureException
                || exception instanceof ExpiredJwtException
                || exception instanceof UnsupportedJwtException) {
            handleException(exception, HttpStatus.BAD_REQUEST);
        }
        exception.printStackTrace();
        throw new RuntimeException(UNEXPECTED_EXCEPTION_MSG);
    }

    private ResponseEntity<?> handleException(Exception exception, HttpStatus status) {
        log.error(exception.getMessage());
        return ResponseEntity.status(status)
                .body(ResponseModel.builder()
                        .status(status)
                        .body(exception.getMessage())
                        .build());
    }

}

