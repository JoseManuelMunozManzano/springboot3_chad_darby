package com.neimerc.springcoredemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
  
  // definir un campo privado para la dependencia
  private Coach myCoach;

  // definir un constructor para la inyección de dependencias.
  // Si solo tenemos un constructor la anotación @Autowired es opcional.
  @Autowired
  public DemoController(Coach theCoach) {
    myCoach = theCoach;
  }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
