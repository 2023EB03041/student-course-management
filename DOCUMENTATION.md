---
title: "Student Course Management System"
subtitle: "Complete Implementation Documentation with Screenshots"
author: "BITS Pilani"
date: "November 13, 2024"
institution: "BITS Pilani"
github: "https://github.com/2023EB03041/student-course-management"
abstract: |
  This document presents the complete implementation of a **Student Course Management System** built using Spring Boot 3.1.5, demonstrating comprehensive CRUD operations with a Many-to-Many relationship between Students and Courses.
  
  **Project Overview:** Students and Courses entities with Many-to-Many relationship. Database with 10 students and 10 courses pre-populated. Full Create, Read, Update functionality. 19 unit tests - all passing. Layered architecture (Entity → Repository → Service → Controller → View).
  
  **Key Achievements:** Complete CRUD operations for both entities, Custom JPQL query with INNER JOIN, Exception handling with user-friendly messages, Modern JSP-based UI with CSS styling, Comprehensive unit testing (100% pass rate), Professional GitHub repository with documentation.
  
  **Technologies Used:** Spring Boot 3.1.5, Spring Data JPA with Hibernate, H2 In-Memory Database, JSP with JSTL, Maven, JUnit 5 and Mockito.
  
  **GitHub Repository:** https://github.com/2023EB03041/student-course-management
---

\newpage

# Entity Relationship Design

## Relationship Type

**Many-to-Many Relationship** between Student and Course entities.

- Each student can enroll in multiple courses
- Each course can have multiple students enrolled
- Implemented using a join table: `student_course`

## Entity Relationship Diagram

![Entity Relationship Diagram](er_diagram.png){ width=100% }

The diagram illustrates the complete entity relationship structure with:

- **Student Entity**: Contains student information (id, name, email, department, semester)
- **Course Entity**: Contains course information (id, courseCode, courseName, credits, description)
- **student_course Join Table**: Manages the many-to-many relationship with foreign keys

## JPA Annotations

**Student Entity**:
- `@Entity` and `@Table(name = "students")`
- `@ManyToMany` with `@JoinTable`
- `FetchType.LAZY` for performance
- Validation: `@NotBlank`, `@Email`, `@Size`

**Course Entity**:
- `@Entity` and `@Table(name = "courses")`
- `@ManyToMany(mappedBy = "courses")`
- `@JsonIgnore` to prevent circular references

\newpage

# Database Schema

## Students Table

```sql
CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    department VARCHAR(255) NOT NULL,
    semester INTEGER NOT NULL
);
```

**Sample Data** (10 rows):

1. Rahul Sharma - Computer Science, Semester 3
2. Priya Patel - Computer Science, Semester 4
3. Amit Kumar - Electronics, Semester 2
4. Neha Singh - Computer Science, Semester 5
5. Vikas Verma - Mathematics, Semester 3
6. Anjali Desai - Computer Science, Semester 2
7. Rohan Gupta - Electronics, Semester 4
8. Kavita Reddy - Computer Science, Semester 6
9. Sanjay Mehta - Physics, Semester 1
10. Deepika Iyer - Computer Science, Semester 3

## Courses Table

```sql
CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(255) NOT NULL UNIQUE,
    course_name VARCHAR(200) NOT NULL,
    credits INTEGER NOT NULL,
    description VARCHAR(500)
);
```

**Sample Data** (10 rows):

1. CS101 - Introduction to Programming (4 credits)
2. CS201 - Data Structures and Algorithms (4 credits)
3. CS301 - Database Management Systems (3 credits)
4. CS302 - Operating Systems (4 credits)
5. CS401 - Software Engineering (3 credits)
6. MA101 - Calculus I (4 credits)
7. MA201 - Linear Algebra (3 credits)
8. PH101 - Physics I (4 credits)
9. EE201 - Digital Electronics (3 credits)
10. CS501 - Machine Learning (4 credits)

## Student_Course Join Table

```sql
CREATE TABLE student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

Multiple student-course relationships established during initialization.

\newpage

# Implementation Architecture

## Layered Architecture

The application follows a clean layered architecture:

1. **Entity Layer**: JPA entities with annotations
2. **Repository Layer**: Spring Data JPA repositories
3. **Service Layer**: Business logic and transaction management
4. **Controller Layer**: Spring MVC controllers
5. **View Layer**: JSP pages with JSTL

## Project Structure

```
src/main/java/com/bitspilani/studentcourse/
+-- entity/
|   +-- Student.java
|   +-- Course.java
+-- repository/
|   +-- StudentRepository.java
|   +-- CourseRepository.java
+-- service/
|   +-- StudentService.java
|   +-- CourseService.java
+-- controller/
|   +-- StudentController.java
|   +-- CourseController.java
|   +-- HomeController.java
+-- dto/
|   +-- StudentCourseDTO.java
+-- DataInitializer.java
+-- StudentCourseManagementApplication.java
```

## Key Components

### Repository Layer
- Extends `JpaRepository<T, ID>`
- Custom query methods
- Built-in CRUD operations

### Service Layer
- `@Service` annotation
- `@Transactional` for transaction management
- Business logic and validation
- Exception handling

### Controller Layer
- `@Controller` annotation
- Request mapping with `@GetMapping` and `@PostMapping`
- Model binding with `@ModelAttribute`
- Flash attributes for messages

\newpage

# CRUD Operations

## Create Operations

### Create Student
**Endpoint**: `POST /students/save`

**Features**:
- Form validation (name, email, department, semester)
- Course enrollment during creation
- Duplicate email detection
- Exception handling

**Code Sample**:
```java
@PostMapping("/save")
public String saveStudent(@Valid @ModelAttribute Student student,
                         BindingResult result,
                         @RequestParam List<Long> courseIds,
                         RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        return "students/form";
    }
    
    try {
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", 
            "Student created successfully!");
        return "redirect:/students";
    } catch (DataIntegrityViolationException e) {
        model.addAttribute("errorMessage", "Email already exists!");
        return "students/form";
    }
}
```

### Create Course
**Endpoint**: `POST /courses/save`

Similar implementation with course code uniqueness validation.

## Read Operations

### List All Students
**Endpoint**: `GET /students`

Displays all students in a table with edit/delete actions.

### List All Courses
**Endpoint**: `GET /courses`

Displays all courses in a table with edit/delete actions.

### Students with Courses (Custom Query)
**Endpoint**: `GET /students/with-courses`

**Custom JPQL Query**:
```java
@Query("SELECT new com.bitspilani.studentcourse.dto.StudentCourseDTO(" +
       "s.id, s.name, s.email, s.department, s.semester, " +
       "c.id, c.courseCode, c.courseName, c.credits) " +
       "FROM Student s " +
       "INNER JOIN s.courses c")
List<StudentCourseDTO> findAllStudentsWithCourses();
```

This query performs an INNER JOIN between Student and Course entities, returning only students who are enrolled in at least one course.

## Update Operations

### Update Student
**Endpoint**: `POST /students/update`

**Features**:
- Pre-populated form with existing data
- Update all fields including course enrollments
- Validation on update
- Error handling

**Code Sample**:
```java
@PostMapping("/update")
public String updateStudent(@Valid @ModelAttribute Student student,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        return "students/edit";
    }
    
    studentService.updateStudent(student);
    redirectAttributes.addFlashAttribute("successMessage", 
        "Student updated successfully!");
    return "redirect:/students";
}
```

### Update Course
**Endpoint**: `POST /courses/update`

Similar implementation for updating course information.

\newpage

# Custom Query Implementation

## INNER JOIN Query

The application implements a custom repository method that performs an INNER JOIN between Student and Course entities.

### StudentRepository Interface

```java
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
```

### StudentCourseDTO Class

```java
public class StudentCourseDTO {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String studentDepartment;
    private Integer studentSemester;
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer courseCredits;
    
    // Constructor, Getters, Setters
}
```

### Query Result

The query returns a flattened view of students with their courses showing all student-course relationships.

\newpage

# Testing Results

## Unit Test Summary

**Total Tests**: 19  
**Passed**: 19  
**Failed**: 0  
**Success Rate**: 100%

## Repository Tests

### StudentRepositoryTest (4 tests)
1. `testSaveStudent()` - Verifies student can be saved to database
2. `testFindById()` - Verifies student can be retrieved by ID
3. `testExistsByEmail()` - Verifies email uniqueness check
4. `testFindAllStudentsWithCourses()` - Verifies INNER JOIN query

### CourseRepositoryTest (3 tests)
1. `testSaveCourse()` - Verifies course can be saved
2. `testFindById()` - Verifies course retrieval
3. `testExistsByCourseCode()` - Verifies course code uniqueness

## Service Tests

### StudentServiceTest (6 tests)
1. `testGetAllStudents()` - Verifies all students retrieved
2. `testGetStudentById()` - Verifies single student retrieval
3. `testSaveStudent()` - Verifies student creation
4. `testSaveStudentWithDuplicateEmail()` - Verifies exception thrown
5. `testUpdateStudent()` - Verifies student update
6. `testDeleteStudent()` - Verifies student deletion

### CourseServiceTest (6 tests)
Similar tests for course service layer

## Test Execution Output

```
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

All tests use:
- `@DataJpaTest` for repository tests
- `@ExtendWith(MockitoExtension.class)` for service tests
- Mockito for mocking dependencies
- AssertJ for assertions

\newpage

# Application Screenshots

This section demonstrates the complete working application with actual screenshots.

## Home Page

**URL**: `http://localhost:8080/`

![Home Page - Landing Page with Navigation](screenshots/Student-Course-Management-System-11-13-2025_02_31_PM.png){ width=100% }

The home page features:
- Modern gradient background design
- Three navigation cards for Students, Courses, and Student-Course relationships
- Clean and professional user interface

\newpage

## Students List

**URL**: `http://localhost:8080/students`

![Students List - Showing All Students](screenshots/Students-List-11-13-2025_02_31_PM.png){ width=100% }

Features displayed:
- Table showing all 10 students with complete information
- Columns: ID, Name, Email, Department, Semester
- Edit and Delete action buttons for each student
- Add New Student button at the top

\newpage

## Add Student Form

**URL**: `http://localhost:8080/students/new`

![Add Student Form - Create New Student](screenshots/Add-Student-11-13-2025_02_32_PM.png){ width=100% }

Form features:
- Input fields: Name, Email, Department, Semester
- Multi-select checkboxes for course enrollment
- Form validation
- Save and Cancel buttons
- Clean form layout

\newpage

## Courses List

**URL**: `http://localhost:8080/courses`

![Courses List - Showing All Courses](screenshots/Courses-List-11-13-2025_02_33_PM.png){ width=100% }

Displays:
- All 10 courses with complete details
- Columns: ID, Course Code, Course Name, Credits, Description
- Edit and Delete buttons
- Add New Course button

\newpage

## Add Course Form

**URL**: `http://localhost:8080/courses/new`

![Add Course Form - Create New Course](screenshots/Add-Course-11-13-2025_02_33_PM.png){ width=100% }

Form includes:
- Course Code input field
- Course Name input field
- Credits input field
- Description textarea
- Save and Cancel buttons
- Validation for required fields

\newpage

## Students with Courses (Inner Join Result)

**URL**: `http://localhost:8080/students/with-courses`

![Students with Courses - Inner Join Query Results](screenshots/Students-with-Courses-11-13-2025_02_34_PM.png){ width=100% }

This view demonstrates:
- Custom INNER JOIN query results
- Student information combined with enrolled courses
- Multiple rows per student (one for each enrolled course)
- Columns: Student ID, Name, Email, Department, Semester, Course Code, Course Name, Credits

\newpage

## H2 Database Console

**URL**: `http://localhost:8080/h2-console`

![H2 Console - Database Inspection Tool](screenshots/H2-Console-11-13-2025_02_36_PM.png){ width=100% }

Database console features:
- Direct database access for verification
- JDBC URL: `jdbc:h2:mem:studentcoursedb`
- SQL query execution capability
- Table browsing and data inspection

\newpage

# Code Samples

## Student Entity

```java
@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private Integer semester;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();
    
    // Constructors, Getters, Setters
}
```

## Course Entity

```java
@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Course code is required")
    @Column(nullable = false, unique = true)
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String courseName;
    
    @Column(nullable = false)
    private Integer credits;
    
    @Column(length = 500)
    private String description;
    
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
    
    // Constructors, Getters, Setters
}
```

## StudentService

```java
@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Student saveStudent(Student student) {
        if (student.getId() == null && 
            studentRepository.existsByEmail(student.getEmail())) {
            throw new DataIntegrityViolationException(
                "Email already exists");
        }
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException(
                "Student not found");
        }
        return studentRepository.save(student);
    }
    
    // Additional methods
}
```

## Data Initialization

```java
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create and save courses
        Course course1 = courseRepository.save(
            new Course("CS101", "Intro to Programming", 4, 
                      "Basics of programming")
        );
        
        // Create students with course enrollments
        Student student1 = new Student(
            "Rahul Sharma", "rahul@example.com", 
            "Computer Science", 3
        );
        student1.setCourses(Arrays.asList(course1, course2));
        studentRepository.save(student1);
        
        // ... 9 more students
    }
}
```

\newpage

# Challenges and Solutions

## Many-to-Many Relationship Issues

**Challenge**: Detached entity errors when saving students with courses

**Solution**:
1. Removed cascade operations from the relationship
2. Saved courses first, then created students with references
3. Ensured all entities were in managed state before creating relationships

## JSP Configuration

**Challenge**: 404 errors when accessing JSP pages

**Solution**:
1. Added `tomcat-embed-jasper` dependency
2. Added `jakarta.servlet.jsp.jstl` dependencies
3. Configured view resolver in `application.properties`:
   ```properties
   spring.mvc.view.prefix=/WEB-INF/jsp/
   spring.mvc.view.suffix=.jsp
   ```

## Circular Reference in JSON

**Challenge**: StackOverflowError during JSON serialization

**Solution**:
- Added `@JsonIgnore` on the inverse side (students list in Course entity)
- Used DTO pattern for complex queries

## Form Data Binding

**Challenge**: Binding multiple course selections from form

**Solution**:
- Used `@RequestParam(value = "courseIds")` with `List<Long>`
- Filtered courses in controller to match selected IDs
- Set filtered list before saving

## Exception Handling

**Challenge**: Generic error messages not user-friendly

**Solution**:
- Added try-catch blocks in controllers
- Used `RedirectAttributes` for success messages
- Model attributes for error messages
- Custom validation in service layer

\newpage

