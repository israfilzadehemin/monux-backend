package com.budgetmanagementapp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseModel<T> {
    HttpStatus status;
    T body;

    public static <T> ResponseModel<T> of(T data, HttpStatus status) {
        return new ResponseModel<>(status, data);
    }

}
