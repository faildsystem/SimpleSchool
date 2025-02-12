package com.example.demo.services;

import com.example.demo.models.Dtos.CreateStudentDto;
import com.example.demo.models.Dtos.UpdateStudentDto;
import com.example.demo.models.domain.Student;
import com.example.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Student.Gender gender = updateStudentDto.getGender();

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

}
