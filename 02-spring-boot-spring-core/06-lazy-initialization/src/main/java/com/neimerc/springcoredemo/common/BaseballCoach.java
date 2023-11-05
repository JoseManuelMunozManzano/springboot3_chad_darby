package com.neimerc.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component
public class BaseballCoach implements Coach {

  // Se añade un diagnóstico para saber cuando se crea este bean
  public BaseballCoach() {
    System.out.println("In constructor: " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Spend 30 minutes in batting practice";
  }
  
}
