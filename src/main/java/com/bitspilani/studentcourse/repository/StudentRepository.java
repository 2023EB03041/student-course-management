package com.bitspilani.studentcourse.repository;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.dto.StudentCourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    @Query("SELECT new com.bitspilani.studentcourse.dto.StudentCourseDTO(" +
           "s.id, s.name, s.email, s.department, s.semester, " +
           "c.id, c.courseCode, c.courseName, c.credits) " +
           "FROM Student s " +
           "INNER JOIN s.courses c")
    List<StudentCourseDTO> findAllStudentsWithCourses();
    
    boolean existsByEmail(String email);
}
