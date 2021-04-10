package com.budgetmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ResponseModel {
    private HttpStatus status;
    private Object body;
}
