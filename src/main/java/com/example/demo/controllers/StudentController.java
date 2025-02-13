package com.example.demo.controllers;

import com.example.demo.models.Dtos.student.CreateStudentDto;
import com.example.demo.models.Dtos.student.UpdateStudentDto;
import com.example.demo.models.domain.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "all")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping(path = "create")
    public Student createStudent(@RequestBody CreateStudentDto createStudentDto){
        return studentService.createStudent(createStudentDto);
    }

    @PutMapping(path = "update/{studentId}")
    public Student updateStudent(@RequestBody UpdateStudentDto updateStudentDto, @PathVariable Long studentId){
        return studentService.updateStudent(studentId, updateStudentDto);
    }

    @DeleteMapping(path = "delete/{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }
}
