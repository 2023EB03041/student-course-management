package com.bitspilani.studentcourse.service;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.dto.StudentCourseDTO;
import com.bitspilani.studentcourse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student saveStudent(Student student) {
        if (student.getId() == null && studentRepository.existsByEmail(student.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    public List<StudentCourseDTO> getStudentsWithCourses() {
        return studentRepository.findAllStudentsWithCourses();
    }
}
