package com.bitspilani.studentcourse.service;

import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.repository.CourseRepository;
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
public class CourseServiceTest {
    
    @Mock
    private CourseRepository courseRepository;
    
    @InjectMocks
    private CourseService courseService;
    
    @Test
    public void testGetAllCourses() {
        Course course1 = new Course("CS101", "Course 1", 3, "Description 1");
        Course course2 = new Course("CS102", "Course 2", 4, "Description 2");
        List<Course> courses = Arrays.asList(course1, course2);
        
        when(courseRepository.findAll()).thenReturn(courses);
        
        List<Course> result = courseService.getAllCourses();
        
        assertThat(result).hasSize(2);
        verify(courseRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetCourseById() {
        Course course = new Course("CS101", "Test Course", 3, "Description");
        course.setId(1L);
        
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        
        Optional<Course> result = courseService.getCourseById(1L);
        
        assertThat(result).isPresent();
        assertThat(result.get().getCourseCode()).isEqualTo("CS101");
        verify(courseRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testSaveCourse() {
        Course course = new Course("CS101", "Test Course", 3, "Description");
        
        when(courseRepository.existsByCourseCode(course.getCourseCode())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        
        Course result = courseService.saveCourse(course);
        
        assertThat(result).isNotNull();
        verify(courseRepository, times(1)).save(course);
    }
    
    @Test
    public void testSaveCourseWithDuplicateCode() {
        Course course = new Course("CS101", "Test Course", 3, "Description");
        
        when(courseRepository.existsByCourseCode(course.getCourseCode())).thenReturn(true);
        
        assertThatThrownBy(() -> courseService.saveCourse(course))
            .isInstanceOf(DataIntegrityViolationException.class)
            .hasMessageContaining("Course code already exists");
    }
    
    @Test
    public void testUpdateCourse() {
        Course course = new Course("CS101", "Test Course", 3, "Description");
        course.setId(1L);
        
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        
        Course result = courseService.updateCourse(course);
        
        assertThat(result).isNotNull();
        verify(courseRepository, times(1)).save(course);
    }
    
    @Test
    public void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(1L);
        
        courseService.deleteCourse(1L);
        
        verify(courseRepository, times(1)).deleteById(1L);
    }
}
