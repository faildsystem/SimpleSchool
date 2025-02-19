package com.example.demo.repositories;

import com.example.demo.models.domain.Role;
import com.example.demo.models.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByName (String name);
}
