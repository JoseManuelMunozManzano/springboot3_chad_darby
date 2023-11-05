package com.neimerc.springcoredemo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.springcoredemo.common.Coach;

@RestController
public class DemoController {
  
  // definir un campo privado para la dependencia
  private Coach myCoach;

  // definir un constructor para la inyección de dependencias.
  // Si solo tenemos un constructor la anotación @Autowired es opcional.
  // @Autowired
  public DemoController(Coach theCoach) {
    myCoach = theCoach;
  }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
