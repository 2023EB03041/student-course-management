package com.bitspilani.studentcourse.service;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;
    
    @InjectMocks
    private StudentService studentService;
    
    @Test
    public void testGetAllStudents() {
        Student student1 = new Student("Student 1", "student1@example.com", "CS", 1);
        Student student2 = new Student("Student 2", "student2@example.com", "EE", 2);
        List<Student> students = Arrays.asList(student1, student2);
        
        when(studentRepository.findAll()).thenReturn(students);
        
        List<Student> result = studentService.getAllStudents();
        
        assertThat(result).hasSize(2);
        verify(studentRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetStudentById() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        student.setId(1L);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        
        Optional<Student> result = studentService.getStudentById(1L);
        
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Student");
        verify(studentRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testSaveStudent() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        Student result = studentService.saveStudent(student);
        
        assertThat(result).isNotNull();
        verify(studentRepository, times(1)).save(student);
    }
    
    @Test
    public void testSaveStudentWithDuplicateEmail() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(true);
        
        assertThatThrownBy(() -> studentService.saveStudent(student))
            .isInstanceOf(DataIntegrityViolationException.class)
            .hasMessageContaining("Email already exists");
    }
    
    @Test
    public void testUpdateStudent() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        student.setId(1L);
        
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        Student result = studentService.updateStudent(student);
        
        assertThat(result).isNotNull();
        verify(studentRepository, times(1)).save(student);
    }
    
    @Test
    public void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);
        
        studentService.deleteStudent(1L);
        
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
