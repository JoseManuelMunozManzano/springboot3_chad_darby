package com.jmmunoz.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jmmunoz.springboot.thymeleafdemo.model.Student;

@Controller
public class StudentController {
  
  @GetMapping("/showStudentForm")
  public String showForm(Model theModel) {

    // crear un objeto student
    Student theStudent = new Student();

    // añadir un objeto student al model
    theModel.addAttribute("student", theStudent);

    return "student-form";
  }

  @PostMapping("/processStudentForm")
  public String processForm(@ModelAttribute("student") Student theStudent) {

    // Mostrar log con la data de entrada
    System.out.println("theStudent: " + theStudent.getFirstName() + " " + theStudent.getLastName());

    return "student-confirmation";
  }
}
