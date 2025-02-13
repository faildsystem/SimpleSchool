package com.example.demo.services;

import com.example.demo.models.Dtos.course.CreateOrUpdateCourseDto;
import com.example.demo.models.domain.Course;
import com.example.demo.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    final CourseRepository  courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course createCourse(CreateOrUpdateCourseDto courseDto){
        String name = courseDto.getName();

        Optional<Course> optionalCourse = courseRepository.findCourseByName(name);

        if (optionalCourse.isPresent()){
            throw new RuntimeException("Course with the same " + name + " exists");
        }

        String description = courseDto.getDescription();


        Course course = new Course(name, description);

        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long courseId, CreateOrUpdateCourseDto courseDto){

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with id " + courseId + " does not exist"));

        String name = courseDto.getName();

        Optional<Course> course = courseRepository.findCourseByName(name);

        if (course.isPresent()){
            throw new RuntimeException("Course with the same " + name + " exists");
        }

        String description = courseDto.getDescription();

        if (name != null && !name.isEmpty() && !Objects.equals(name, existingCourse.getName())){
            existingCourse.setName(name);
        }

        if (description != null && !description.isEmpty() && !Objects.equals(description, existingCourse.getDescription())){
            existingCourse.setDescription(description);
        }

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long courseId){
        boolean exists = courseRepository.existsById(courseId);

        if(exists){
            courseRepository.deleteById(courseId);
        }
        else{
            throw new RuntimeException("Course with id " + courseId + " does not exist");
        }
    }
}
