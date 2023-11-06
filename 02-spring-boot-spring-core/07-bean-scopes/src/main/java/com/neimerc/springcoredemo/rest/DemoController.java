package com.neimerc.springcoredemo.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.springcoredemo.common.Coach;

@RestController
public class DemoController {
  
  // definir un campo privado para la dependencia
  private Coach myCoach;
  // definir otro campo privado para comprobar el scope de un bean
  private Coach anotherCoach;

  // Recordar que con solo un constructor es opcional indicar la anotación @Autowired
  // @Autowired
  public DemoController(
    @Qualifier("cricketCoach") Coach theCoach,
    @Qualifier("cricketCoach") Coach theAnotherCoach
    ) {
    myCoach = theCoach;
    anotherCoach = theAnotherCoach;
  }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }

  @GetMapping("/check")
  public String check() {
    // singleton -> true
    // prototype -> false
    return "Comparing beans: myCoach == anotherCoach, " + (myCoach == anotherCoach);
  }
}
