package com.bitspilani.studentcourse.repository;

import com.bitspilani.studentcourse.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CourseRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Test
    public void testSaveCourse() {
        Course course = new Course("TEST101", "Test Course", 3, "Test Description");
        Course saved = courseRepository.save(course);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCourseCode()).isEqualTo("TEST101");
        assertThat(saved.getCourseName()).isEqualTo("Test Course");
    }
    
    @Test
    public void testFindById() {
        Course course = new Course("TEST101", "Test Course", 3, "Test Description");
        entityManager.persist(course);
        entityManager.flush();
        
        Course found = courseRepository.findById(course.getId()).orElse(null);
        
        assertThat(found).isNotNull();
        assertThat(found.getCourseCode()).isEqualTo("TEST101");
    }
    
    @Test
    public void testExistsByCourseCode() {
        Course course = new Course("TEST101", "Test Course", 3, "Test Description");
        entityManager.persist(course);
        entityManager.flush();
        
        boolean exists = courseRepository.existsByCourseCode("TEST101");
        boolean notExists = courseRepository.existsByCourseCode("NOTFOUND");
        
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
