package com.example.demo.models.Dtos;

import com.example.demo.models.domain.Student;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class CreateStudentDto {

    private String email;

    private String name;

    private LocalDate birthday;

    public enum Gender {
        MALE, FEMALE
    }

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
