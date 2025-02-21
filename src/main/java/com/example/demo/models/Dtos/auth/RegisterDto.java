package com.example.demo.models.Dtos.auth;

import com.example.demo.helpers.Enums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDto {

    private String email;

    private String username;
    
    private String password;

    private String name;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Enums.Gender gender;
}
