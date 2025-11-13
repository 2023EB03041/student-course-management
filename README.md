# Student Course Management System

A comprehensive Spring Boot web application for managing student and course information with full CRUD operations.

## Overview

This application demonstrates a complete implementation of a Spring Boot MVC application with JPA/Hibernate for database management, JSP for views, and a many-to-many relationship between Students and Courses.

## Technologies Used

- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **Hibernate ORM**
- **H2 Database** (In-memory)
- **JSP & JSTL**
- **Maven**
- **JUnit 5 & Mockito** (for testing)
- **Bootstrap-inspired CSS** (custom styling)

## Features

### Entity Management
- **Student Entity**: Manages student information including name, email, department, and semester
- **Course Entity**: Manages course information including course code, name, credits, and description
- **Many-to-Many Relationship**: Students can enroll in multiple courses, and courses can have multiple students

### CRUD Operations

#### Create
- Add new students with validation
- Add new courses with validation
- Enroll students in multiple courses during creation
- Exception handling for duplicate emails and course codes

#### Read
- List all students
- List all courses
- View student-course relationships using custom JOIN query
- Inner join query displaying all students with their enrolled courses

#### Update
- Update student information
- Update course information
- Modify student course enrollments
- Validation and error handling

### Additional Features
- **Data Initialization**: Pre-populated database with 10 students and 10 courses
- **Custom Repository Query**: JPQL query with INNER JOIN between students and courses
- **Exception Handling**: Proper handling of data integrity violations
- **Responsive UI**: Clean, modern interface with CSS styling
- **Unit Tests**: Comprehensive tests for repository and service layers

## Project Structure

```
src/
├── main/
│   ├── java/com/bitspilani/studentcourse/
│   │   ├── controller/
│   │   │   ├── StudentController.java
│   │   │   ├── CourseController.java
│   │   │   └── HomeController.java
│   │   ├── entity/
│   │   │   ├── Student.java
│   │   │   └── Course.java
│   │   ├── repository/
│   │   │   ├── StudentRepository.java
│   │   │   └── CourseRepository.java
│   │   ├── service/
│   │   │   ├── StudentService.java
│   │   │   └── CourseService.java
│   │   ├── dto/
│   │   │   └── StudentCourseDTO.java
│   │   ├── DataInitializer.java
│   │   └── StudentCourseManagementApplication.java
│   ├── resources/
│   │   └── application.properties
│   └── webapp/WEB-INF/jsp/
│       ├── index.jsp
│       ├── students/
│       │   ├── list.jsp
│       │   ├── form.jsp
│       │   ├── edit.jsp
│       │   └── with-courses.jsp
│       └── courses/
│           ├── list.jsp
│           ├── form.jsp
│           └── edit.jsp
└── test/
    └── java/com/bitspilani/studentcourse/
        ├── repository/
        │   ├── StudentRepositoryTest.java
        │   └── CourseRepositoryTest.java
        └── service/
            ├── StudentServiceTest.java
            └── CourseServiceTest.java
```

## Database Schema

### Students Table
- `id` (BIGINT, Primary Key, Auto-increment)
- `name` (VARCHAR(100), NOT NULL)
- `email` (VARCHAR(255), NOT NULL, UNIQUE)
- `department` (VARCHAR(255), NOT NULL)
- `semester` (INTEGER, NOT NULL)

### Courses Table
- `id` (BIGINT, Primary Key, Auto-increment)
- `course_code` (VARCHAR(255), NOT NULL, UNIQUE)
- `course_name` (VARCHAR(200), NOT NULL)
- `credits` (INTEGER, NOT NULL)
- `description` (VARCHAR(500))

### Student_Course Table (Join Table)
- `student_id` (BIGINT, Foreign Key → students.id)
- `course_id` (BIGINT, Foreign Key → courses.id)

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps to Run

1. Clone the repository:
```bash
git clone https://github.com/2023EB03041/student-course-management.git
cd student-course-management
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
- Main application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:studentcoursedb`
  - Username: `sa`
  - Password: (leave empty)

## API Endpoints

### Student Endpoints
- `GET /` - Home page
- `GET /students` - List all students
- `GET /students/new` - Show create student form
- `POST /students/save` - Save new student
- `GET /students/edit/{id}` - Show edit student form
- `POST /students/update` - Update student
- `GET /students/delete/{id}` - Delete student
- `GET /students/with-courses` - View students with courses (Inner Join)

### Course Endpoints
- `GET /courses` - List all courses
- `GET /courses/new` - Show create course form
- `POST /courses/save` - Save new course
- `GET /courses/edit/{id}` - Show edit course form
- `POST /courses/update` - Update course
- `GET /courses/delete/{id}` - Delete course

## Testing

Run all tests:
```bash
mvn test
```

Test coverage includes:
- Repository layer tests (with @DataJpaTest)
- Service layer tests (with Mockito)
- Custom query method tests

## Entity Relationship Design

```
Student *-------* Course
   |                |
   |                |
1:*              *:1
   |                |
   +---Student_Course---+
       (Join Table)
```

- **Relationship**: Many-to-Many
- **Owning Side**: Student entity
- **Join Table**: student_course
- **Cascade**: No cascade operations (courses are independent)
- **Fetch Type**: LAZY (for better performance)

## Key Implementation Details

### Custom Repository Query
```java
@Query("SELECT new com.bitspilani.studentcourse.dto.StudentCourseDTO(" +
       "s.id, s.name, s.email, s.department, s.semester, " +
       "c.id, c.courseCode, c.courseName, c.credits) " +
       "FROM Student s " +
       "INNER JOIN s.courses c")
List<StudentCourseDTO> findAllStudentsWithCourses();
```

### Exception Handling
- Duplicate email validation in StudentService
- Duplicate course code validation in CourseService
- DataIntegrityViolationException handling in controllers
- User-friendly error messages displayed in JSP views

### Validation
- Bean Validation annotations (@NotBlank, @Email, @Size)
- Form validation with BindingResult
- Server-side validation in service layer

## Sample Data

The application comes pre-populated with:
- 10 Students from various departments
- 10 Courses covering CS, Mathematics, Physics, and Electronics
- Multiple student-course enrollments demonstrating the many-to-many relationship

## Challenges Faced & Solutions

1. **Challenge**: Many-to-many relationship causing detached entity errors
   - **Solution**: Removed cascade operations and saved courses before creating student-course relationships

2. **Challenge**: JSP pages not rendering
   - **Solution**: Added Jasper and JSTL dependencies, configured view resolver in application.properties

3. **Challenge**: H2 console access issues
   - **Solution**: Enabled H2 console in application.properties and configured proper database URL

4. **Challenge**: Circular reference in JSON serialization
   - **Solution**: Added @JsonIgnore annotation on the inverse side of the relationship

## Future Enhancements

- Add pagination for large datasets
- Implement search and filter functionality
- Add user authentication and authorization
- Create REST API endpoints
- Add file upload for bulk data import
- Implement soft delete functionality
- Add audit fields (created_at, updated_at)

## Author

**Institution**: BITS Pilani

## License

This project is created for educational purposes as part of coursework assignment.
