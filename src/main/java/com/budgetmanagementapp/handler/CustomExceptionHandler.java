package com.budgetmanagementapp.handler;


import com.budgetmanagementapp.exception.*;
import com.budgetmanagementapp.model.ErrorResponseModel;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class, AppException.class})
    public ResponseEntity<?> handleException(Exception exception, AppException appException) {
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
                || exception instanceof DuplicateLabelException
                || exception instanceof TransferToSelfException
                || exception instanceof MethodArgumentNotValidException
                || exception instanceof NotEnoughBalanceException
                || exception instanceof InvalidPhoneNumberException
                || exception instanceof ResetPasswordException) {
            return handleException(appException, HttpStatus.BAD_REQUEST);

        } else if (exception instanceof UserRoleNotFoundException
                || exception instanceof UserNotFoundException
                || exception instanceof AccountTypeNotFoundException
                || exception instanceof CategoryNotFoundException
                || exception instanceof LabelNotFoundException
                || exception instanceof FeedbackNotFoundException
                || exception instanceof CategoryTypeNotFoundException
                || exception instanceof TransactionTypeNotFoundException
                || exception instanceof TransactionNotFoundException
                || exception instanceof TemplateNotFoundException
                || exception instanceof CurrencyNotFoundException
                || exception instanceof AccountNotFoundException
                || exception instanceof NoExistingTransactionException
        ) {
            return handleException(appException, HttpStatus.NOT_FOUND);
        }

        throw new RuntimeException(String.valueOf(exception.getClass()));
    }

    private ResponseEntity<?> handleException(AppException exception, HttpStatus status) {
        log.error(exception.getMessage());
        return ResponseEntity.status(status)
                .body(ResponseModel.builder()
                        .status(status)
                        .body(ErrorResponseModel.builder()
                                .message(exception.getMessage())
                                .code(exception.getCode())
                                .build())
                        .build());
    }

}

