package com.bitspilani.studentcourse.repository;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.dto.StudentCourseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Test
    public void testSaveStudent() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        Student saved = studentRepository.save(student);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Student");
        assertThat(saved.getEmail()).isEqualTo("test@example.com");
    }
    
    @Test
    public void testFindById() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        entityManager.persist(student);
        entityManager.flush();
        
        Student found = studentRepository.findById(student.getId()).orElse(null);
        
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Student");
    }
    
    @Test
    public void testExistsByEmail() {
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        entityManager.persist(student);
        entityManager.flush();
        
        boolean exists = studentRepository.existsByEmail("test@example.com");
        boolean notExists = studentRepository.existsByEmail("notfound@example.com");
        
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
    
    @Test
    public void testFindAllStudentsWithCourses() {
        Course course = new Course("TEST101", "Test Course", 3, "Test Description");
        entityManager.persist(course);
        
        Student student = new Student("Test Student", "test@example.com", "CS", 1);
        student.addCourse(course);
        entityManager.persist(student);
        entityManager.flush();
        
        List<StudentCourseDTO> results = studentRepository.findAllStudentsWithCourses();
        
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getStudentName()).isEqualTo("Test Student");
        assertThat(results.get(0).getCourseCode()).isEqualTo("TEST101");
    }
}
