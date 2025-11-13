package com.bitspilani.studentcourse.controller;

import com.bitspilani.studentcourse.entity.Course;
import com.bitspilani.studentcourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/form";
    }
    
    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "courses/form";
        }
        
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course created successfully!");
            return "redirect:/courses";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Course code already exists!");
            return "courses/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "courses/form";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return courseService.getCourseById(id)
            .map(course -> {
                model.addAttribute("course", course);
                return "courses/edit";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Course not found!");
                return "redirect:/courses";
            });
    }
    
    @PostMapping("/update")
    public String updateCourse(@Valid @ModelAttribute("course") Course course,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "courses/edit";
        }
        
        try {
            courseService.updateCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
            return "redirect:/courses";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "courses/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/courses";
    }
}
