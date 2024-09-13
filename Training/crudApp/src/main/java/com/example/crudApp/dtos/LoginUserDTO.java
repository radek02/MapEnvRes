package com.example.crudApp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDTO {
    private String username;
    private String password;
}
