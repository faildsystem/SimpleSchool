package com.example.demo.models.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String name;

    @Transient
    private Integer age;

    @Column(nullable = false)

    private LocalDate birthday;

    public enum Gender {
        MALE, FEMALE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;


    public Student(String name, String email, LocalDate birthday, Gender gender) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Integer getAge() {
        return (birthday != null) ? Period.between(this.birthday, LocalDate.now()).getYears() : null;
    }

//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", email='" + email + '\'' +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", birthday=" + birthday +
//                ", gender=" + gender +
//                '}';
//    }
}
