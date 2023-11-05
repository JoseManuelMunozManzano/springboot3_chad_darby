package com.neimerc.springcoredemo.rest;

import org.springframework.beans.factory.annotation.Qualifier;
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
  // Como hay más de una implementación de la interface Coach, usamos la anotación
  // @Qualifier para indicar cual inyectamos. Se usa el nombre de la clase, pero
  // con el primer carácter en minúsculas.
  public DemoController(@Qualifier("cricketCoach") Coach theCoach) {
    myCoach = theCoach;
  }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
