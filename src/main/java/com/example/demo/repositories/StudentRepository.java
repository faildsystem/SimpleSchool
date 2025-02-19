package com.example.demo.repositories;

import com.example.demo.models.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail (String email);

    Boolean existsByEmail (String email);
}
