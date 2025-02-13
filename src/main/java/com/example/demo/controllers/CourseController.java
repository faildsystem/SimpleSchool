package com.example.demo.controllers;

import com.example.demo.models.Dtos.course.CreateOrUpdateCourseDto;
import com.example.demo.models.Dtos.student.UpdateStudentDto;
import com.example.demo.models.domain.Course;
import com.example.demo.models.domain.Student;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "all")
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @PostMapping(path = "create")
    public Course createCourse(@RequestBody CreateOrUpdateCourseDto courseDto){
        return courseService.createCourse(courseDto);
    }

    @PutMapping(path = "update/{courseId}")
    public Course updateCourse(@RequestBody CreateOrUpdateCourseDto courseDto, @PathVariable Long courseId){
        return courseService.updateCourse(courseId, courseDto);
    }

    @DeleteMapping(path = "delete/{courseId}")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
    }
}
