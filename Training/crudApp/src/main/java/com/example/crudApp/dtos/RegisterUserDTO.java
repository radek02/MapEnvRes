package com.example.crudApp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDTO {
    private String username;
    private String password;
}
