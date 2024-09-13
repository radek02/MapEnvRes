package com.example.crudApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private String status;
    private T response;
}
