package com.example.demo.services;

import com.example.demo.helpers.Enums;
import com.example.demo.models.Dtos.auth.RegisterDto;
import com.example.demo.models.Dtos.student.UpdateStudentDto;
import com.example.demo.models.domain.Course;
import com.example.demo.models.domain.Student;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StudentService {

    final StudentRepository studentRepository;
    final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student createStudent(RegisterDto registerDto){
        String email = registerDto.getEmail();

        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(email);

        if (optionalStudent.isPresent()){
            throw new RuntimeException("Student with the same " + email + " exists");
        }

        String name = registerDto.getName();
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        LocalDate birthday = registerDto.getBirthday();
        Enums.Gender gender = registerDto.getGender();

        Student student = new Student(name, email, username, password, birthday, gender);
        
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long studentId, UpdateStudentDto updateStudentDto){

        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student with id " + studentId + " does not exist"));

        String email = updateStudentDto.getEmail();

        Optional<Student> student = studentRepository.findStudentByEmail(email);

        if (student.isPresent()){
            throw new RuntimeException("Student with the same " + email + " exists");
        }

        String name = updateStudentDto.getName();
        LocalDate birthday = updateStudentDto.getBirthday();
        Enums.Gender gender = updateStudentDto.getGender();

        if (name != null && !name.isEmpty() && !Objects.equals(name, existingStudent.getName())){
            existingStudent.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(email, existingStudent.getEmail())){
            existingStudent.setEmail(email);
        }

        if (gender != null && !Objects.equals(gender, existingStudent.getGender())){
            existingStudent.setGender(gender);
        }

        if (birthday != null && !Objects.equals(birthday, existingStudent.getBirthday())){
            existingStudent.setBirthday(birthday);
        }

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long studentId){
        boolean exists = studentRepository.existsById(studentId);

        if(exists){
            studentRepository.deleteById(studentId);
        }
        else{
            throw new RuntimeException("Student with id " + studentId + " does not exist");
        }
    }

    public Student enrollCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + courseId));

        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            course.getStudents().add(student);
        }
        return studentRepository.save(student);
    }

    public Student withdrawCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + courseId));

        if (student.getCourses().contains(course)) {
            student.getCourses().remove(course);
            course.getStudents().remove(student);
        } else {
            throw new IllegalStateException("Student is not enrolled in the course.");
        }

        return studentRepository.save(student);
    }
}
