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

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public Student createStudent(@RequestBody CreateStudentDto createStudentDto){
        return studentService.createStudent(createStudentDto);
    }

    @PutMapping(path = "{studentId}")
    public Student updateStudent(@RequestBody UpdateStudentDto updateStudentDto, @PathVariable Long studentId){
        return studentService.updateStudent(studentId, updateStudentDto);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public Student enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentService.enrollCourse(studentId, courseId);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public Student withdrawCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentService.withdrawCourse(studentId, courseId);
    }
}
