package com.neimerc.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

  // Se añade un diagnóstico para saber cuando se crea este bean
  public TennisCoach() {
    System.out.println("In constructor: " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Practice your backend volley";
  }
  
}
