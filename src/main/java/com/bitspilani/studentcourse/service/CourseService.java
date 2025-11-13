package com.bitspilani.studentcourse.service;

import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Course saveCourse(Course course) {
        if (course.getId() == null && courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new DataIntegrityViolationException("Course code already exists");
        }
        return courseRepository.save(course);
    }
    
    public Course updateCourse(Course course) {
        if (!courseRepository.existsById(course.getId())) {
            throw new IllegalArgumentException("Course not found with id: " + course.getId());
        }
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
