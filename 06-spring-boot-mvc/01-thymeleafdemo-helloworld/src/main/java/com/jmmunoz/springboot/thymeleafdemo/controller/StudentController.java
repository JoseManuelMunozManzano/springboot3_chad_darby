package com.jmmunoz.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jmmunoz.springboot.thymeleafdemo.model.Student;

@Controller
public class StudentController {

  @Value("${countries}")
  private List<String> countries;

  @Value("${languages}")
  private List<String> languages;
  
  @GetMapping("/showStudentForm")
  public String showForm(Model theModel) {

    // crear un objeto student
    Student theStudent = new Student();

    // añadir un objeto student al model
    theModel.addAttribute("student", theStudent);

    // añadir lista de paises al model
    theModel.addAttribute("countries", countries);

    // añadir lista de lenguajes de programación al model
    theModel.addAttribute("languages", languages);

    return "student-form";
  }

  @PostMapping("/processStudentForm")
  public String processForm(@ModelAttribute("student") Student theStudent) {

    // Mostrar log con la data de entrada
    System.out.println("theStudent: " + theStudent.getFirstName() + " " + theStudent.getLastName());

    return "student-confirmation";
  }
}
