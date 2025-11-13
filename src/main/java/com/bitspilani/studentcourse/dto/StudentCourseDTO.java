package com.bitspilani.studentcourse.dto;

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
    
    public StudentCourseDTO() {
    }
    
    public StudentCourseDTO(Long studentId, String studentName, String studentEmail, 
                           String studentDepartment, Integer studentSemester,
                           Long courseId, String courseCode, String courseName, Integer courseCredits) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentDepartment = studentDepartment;
        this.studentSemester = studentSemester;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
    }
    
    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public String getStudentDepartment() {
        return studentDepartment;
    }
    
    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }
    
    public Integer getStudentSemester() {
        return studentSemester;
    }
    
    public void setStudentSemester(Integer studentSemester) {
        this.studentSemester = studentSemester;
    }
    
    public Long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public Integer getCourseCredits() {
        return courseCredits;
    }
    
    public void setCourseCredits(Integer courseCredits) {
        this.courseCredits = courseCredits;
    }
}
