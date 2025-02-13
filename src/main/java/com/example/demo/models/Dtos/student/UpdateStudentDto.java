package com.example.demo.models.Dtos.student;

import com.example.demo.helpers.Enums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateStudentDto {
    private String email;

    private String name;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Enums.Gender gender;
}
