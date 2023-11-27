package com.jmmunoz.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
