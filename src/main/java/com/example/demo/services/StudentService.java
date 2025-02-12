package com.example.demo.services;

import com.example.demo.models.Dtos.CreateStudentDto;
import com.example.demo.models.domain.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student createStudent(Student student){

        return studentRepository.save(student);
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
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

}
