package com.example.demo.controllers;

import com.example.demo.models.Dtos.auth.RegisterDto;
import com.example.demo.models.domain.Role;
import com.example.demo.models.domain.Student;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private StudentRepository studentRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(StudentRepository studentRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        boolean emailExists = studentRepository.existsByEmail(registerDto.getEmail());
        if (emailExists) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

        boolean usernameExists = studentRepository.existsByUsername(registerDto.getUsername());
        if (usernameExists) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        String password = passwordEncoder.encode(registerDto.getPassword());

        Role role = roleRepository.findRoleByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Student student = new Student(registerDto.getName(), registerDto.getEmail(), registerDto.getUsername(), password, registerDto.getBirthday(), registerDto.getGender());
        student.setRoles(Collections.singletonList(role));

        studentRepository.save(student);

        return ResponseEntity.ok("User registered successfully!");
    }

}
