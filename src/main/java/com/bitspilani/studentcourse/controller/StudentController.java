package com.bitspilani.studentcourse.controller;

import com.bitspilani.studentcourse.entity.Student;
import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.service.StudentService;
import com.bitspilani.studentcourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAllCourses());
        return "students/form";
    }
    
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                             BindingResult result,
                             @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/form";
        }
        
        try {
            if (courseIds != null && !courseIds.isEmpty()) {
                List<Course> courses = courseService.getAllCourses().stream()
                    .filter(c -> courseIds.contains(c.getId()))
                    .toList();
                student.setCourses(courses);
            }
            
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student created successfully!");
            return "redirect:/students";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Email already exists!");
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/form";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.getStudentById(id)
            .map(student -> {
                model.addAttribute("student", student);
                model.addAttribute("courses", courseService.getAllCourses());
                return "students/edit";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
                return "redirect:/students";
            });
    }
    
    @PostMapping("/update")
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                               BindingResult result,
                               @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/edit";
        }
        
        try {
            if (courseIds != null && !courseIds.isEmpty()) {
                List<Course> courses = courseService.getAllCourses().stream()
                    .filter(c -> courseIds.contains(c.getId()))
                    .toList();
                student.setCourses(courses);
            }
            
            studentService.updateStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/students";
    }
    
    @GetMapping("/with-courses")
    public String listStudentsWithCourses(Model model) {
        model.addAttribute("studentCourses", studentService.getStudentsWithCourses());
        return "students/with-courses";
    }
}
