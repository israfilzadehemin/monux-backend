package com.budgetmanagementapp.handler;


import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.AccountTypeNotFoundException;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.CategoryTypeNotFoundException;
import com.budgetmanagementapp.exception.CurrencyNotFoundException;
import com.budgetmanagementapp.exception.DuplicateAccountException;
import com.budgetmanagementapp.exception.DuplicateCategoryException;
import com.budgetmanagementapp.exception.DuplicateTagException;
import com.budgetmanagementapp.exception.ExpiredOtpException;
import com.budgetmanagementapp.exception.FeedbackNotFoundException;
import com.budgetmanagementapp.exception.GenericException;
import com.budgetmanagementapp.exception.InitialAccountExistingException;
import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.InvalidOtpException;
import com.budgetmanagementapp.exception.InvalidPhoneNumberException;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.PasswordNotSufficientException;
import com.budgetmanagementapp.exception.TagNotFoundException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.exception.UserRoleNotFoundException;
import com.budgetmanagementapp.exception.UsernameNotUniqueException;
import com.budgetmanagementapp.model.ResponseModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        if (exception instanceof InvalidModelException
                || exception instanceof HttpMessageNotReadableException
                || exception instanceof MissingServletRequestParameterException
                || exception instanceof HttpRequestMethodNotSupportedException
                || exception instanceof InvalidOtpException
                || exception instanceof ExpiredOtpException
                || exception instanceof GenericException
                || exception instanceof MalformedJwtException
                || exception instanceof SignatureException
                || exception instanceof ExpiredJwtException
                || exception instanceof UnsupportedJwtException
                || exception instanceof UsernameNotUniqueException
                || exception instanceof PasswordMismatchException
                || exception instanceof InitialAccountExistingException
                || exception instanceof PasswordNotSufficientException
                || exception instanceof InvalidEmailException
                || exception instanceof DuplicateAccountException
                || exception instanceof DuplicateCategoryException
                || exception instanceof DuplicateTagException
                || exception instanceof InvalidPhoneNumberException) {
            return handleException(exception, HttpStatus.BAD_REQUEST);

        } else if (exception instanceof UserRoleNotFoundException
                || exception instanceof UserNotFoundException
                || exception instanceof AccountTypeNotFoundException
                || exception instanceof CategoryNotFoundException
                || exception instanceof TagNotFoundException
                || exception instanceof FeedbackNotFoundException
                || exception instanceof CategoryTypeNotFoundException
                || exception instanceof CurrencyNotFoundException
                || exception instanceof AccountNotFoundException
        ) {
            return handleException(exception, HttpStatus.NOT_FOUND);
        }

        throw new RuntimeException(String.valueOf(exception.getClass()));
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

