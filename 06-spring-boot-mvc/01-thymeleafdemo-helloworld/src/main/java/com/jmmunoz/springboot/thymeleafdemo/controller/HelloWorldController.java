package com.jmmunoz.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {
  
  // método para mostrar formulario HTML
  @RequestMapping("/showForm")
  public String showForm() {
    return "helloworld-form";
  }
  
  // método para procesar formulario HTML
  @RequestMapping("/processForm")
  public String processForm() {
    return "helloworld";
  }

  // método para leer data del formulario y añadir data al modelo
  @RequestMapping("/processFormVersionTwo")
  public String letsShoutDude(HttpServletRequest request, Model model) {
    
    // leer el parámetro del request del formulario HTTP
    String theName = request.getParameter("studentName");

    // convertir la data a mayúsculas
    theName = theName.toUpperCase();

    // crear message
    String result = "Yo! " + theName;
    
    // añadir message al modelo
    model.addAttribute("message", result);

    return "helloworld";
  }
}
