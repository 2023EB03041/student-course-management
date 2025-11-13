package com.bitspilani.studentcourse;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.repository.StudentRepository;
import com.bitspilani.studentcourse.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create and save Courses first
        Course course1 = courseRepository.save(new Course("CS101", "Introduction to Programming", 4, "Basics of programming using Java"));
        Course course2 = courseRepository.save(new Course("CS201", "Data Structures and Algorithms", 4, "Study of data structures and algorithms"));
        Course course3 = courseRepository.save(new Course("CS301", "Database Management Systems", 3, "Relational databases and SQL"));
        Course course4 = courseRepository.save(new Course("CS302", "Operating Systems", 4, "Process management, memory management"));
        Course course5 = courseRepository.save(new Course("CS401", "Software Engineering", 3, "Software development lifecycle"));
        Course course6 = courseRepository.save(new Course("MA101", "Calculus I", 4, "Differential and integral calculus"));
        Course course7 = courseRepository.save(new Course("MA201", "Linear Algebra", 3, "Vector spaces and matrices"));
        Course course8 = courseRepository.save(new Course("PH101", "Physics I", 4, "Mechanics and thermodynamics"));
        Course course9 = courseRepository.save(new Course("EE201", "Digital Electronics", 3, "Logic gates and circuits"));
        Course course10 = courseRepository.save(new Course("CS501", "Machine Learning", 4, "Introduction to ML algorithms"));
        
        // Create Students with saved courses
        Student student1 = new Student("Rahul Sharma", "rahul.sharma@example.com", "Computer Science", 3);
        student1.setCourses(Arrays.asList(course1, course2, course3));
        studentRepository.save(student1);
        
        Student student2 = new Student("Priya Patel", "priya.patel@example.com", "Computer Science", 4);
        student2.setCourses(Arrays.asList(course2, course4, course5));
        studentRepository.save(student2);
        
        Student student3 = new Student("Amit Kumar", "amit.kumar@example.com", "Electronics", 2);
        student3.setCourses(Arrays.asList(course6, course8, course9));
        studentRepository.save(student3);
        
        Student student4 = new Student("Neha Singh", "neha.singh@example.com", "Computer Science", 5);
        student4.setCourses(Arrays.asList(course3, course5, course10));
        studentRepository.save(student4);
        
        Student student5 = new Student("Vikas Verma", "vikas.verma@example.com", "Mathematics", 3);
        student5.setCourses(Arrays.asList(course6, course7));
        studentRepository.save(student5);
        
        Student student6 = new Student("Anjali Desai", "anjali.desai@example.com", "Computer Science", 2);
        student6.setCourses(Arrays.asList(course1, course6, course8));
        studentRepository.save(student6);
        
        Student student7 = new Student("Rohan Gupta", "rohan.gupta@example.com", "Electronics", 4);
        student7.setCourses(Arrays.asList(course4, course9));
        studentRepository.save(student7);
        
        Student student8 = new Student("Kavita Reddy", "kavita.reddy@example.com", "Computer Science", 6);
        student8.setCourses(Arrays.asList(course5, course10));
        studentRepository.save(student8);
        
        Student student9 = new Student("Sanjay Mehta", "sanjay.mehta@example.com", "Physics", 1);
        student9.setCourses(Arrays.asList(course6, course8));
        studentRepository.save(student9);
        
        Student student10 = new Student("Deepika Iyer", "deepika.iyer@example.com", "Computer Science", 3);
        student10.setCourses(Arrays.asList(course2, course3, course4));
        studentRepository.save(student10);
        
        System.out.println("Database initialized with sample data!");
    }
}
