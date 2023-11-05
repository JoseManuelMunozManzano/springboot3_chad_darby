package com.neimerc.springcoredemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.springcoredemo.common.Coach;

@RestController
public class DemoController {
  
  // definir un campo privado para la dependencia
  private Coach myCoach;

  // Inyección de dependencia usando setter y la anotación @Autowired 
  @Autowired
  public void setCoach(Coach theCoach) {
    myCoach = theCoach;
  }

  // Se puede realizar la inyección en cualquier método, no solo en un método setter.
  // Es importante indicar la anotación @Autowired
  // Pero lo normal es utilizar el método setter.
  //
  // @Autowired
  // public void doSomeStuff(Coach theCoach) {
  //   myCoach = theCoach;
  // }

  @GetMapping("/dailyworkout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
