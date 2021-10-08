package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.MsgConstant.INVALID_DATE_TIME_FORMAT_MSG;

import com.budgetmanagementapp.exception.InvalidModelException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Component
public class CustomFormatter {

    public static LocalDateTime stringToLocalDateTime(String value) {
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException exception) {
            throw new InvalidModelException(INVALID_DATE_TIME_FORMAT_MSG);
        }
    }

    public static LocalDate stringToLocalDate(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException exception) {
            throw new InvalidModelException(INVALID_DATE_TIME_FORMAT_MSG);
        }
    }
}
