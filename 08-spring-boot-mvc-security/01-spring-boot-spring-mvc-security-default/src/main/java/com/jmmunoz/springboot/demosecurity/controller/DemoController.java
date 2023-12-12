package com.jmmunoz.springboot.demosecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

  @GetMapping("/")
  public String showHome() {

    return "home";
  }

  // añadir un request mapping para /leaders
  @GetMapping("/leaders")
  public String showLeaders() {

    return "leaders";
  }

  // añadir un request mapping para /systems
  @GetMapping("/systems")
  public String showSystems() {

    return "systems";
  }
}
