package com.jmmunoz.springboot.demo.mycoolapp.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

  @Value("${coach.name}")
  private String coachName;

  @Value("${team.name}")
  private String teamName;
  
  // expone "/" que devuelve "Hello World!"  
  @GetMapping("/")
  public String sayHello() {
    return "Hello " + coachName;
  }
}
