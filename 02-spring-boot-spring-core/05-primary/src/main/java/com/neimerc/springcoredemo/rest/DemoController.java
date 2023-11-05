package com.neimerc.springcoredemo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.springcoredemo.common.Coach;

@RestController
public class DemoController {
  
  // definir un campo privado para la dependencia
  private Coach myCoach;

  // Recordar que con solo un constructor es opcional indicar la anotación @Autowired
  // @Autowired
  //
  // Se está inyectando la implementación de Coach que tiene la anotación @Primary
  public DemoController(Coach theCoach) {
    myCoach = theCoach;
  }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
