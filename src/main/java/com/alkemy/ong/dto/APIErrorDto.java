package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class APIErrorDto {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
