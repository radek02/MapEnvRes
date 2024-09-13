package com.example.crudApp.handlers;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ApiResponseStatusDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationServiceExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponseDTO<?>> UserNotFoundExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), exception.getMessage()));
    }
}
