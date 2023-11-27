package com.jmmunoz.springboot.thymeleafdemo.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
  
  // crear mapeo para "/hello"
  @GetMapping("/hello")
  public String sayHello(Model theModel) {
    
    theModel.addAttribute("theDate", new Date());

    // Como tenemos la dependencia Thymeleaf añadida al POM, por defecto Spring Boot
    // lo configura, y ahora buscará en src/main/resources/templates/ el fichero 
    // helloworld.html
    return "helloworld";
  }
}
