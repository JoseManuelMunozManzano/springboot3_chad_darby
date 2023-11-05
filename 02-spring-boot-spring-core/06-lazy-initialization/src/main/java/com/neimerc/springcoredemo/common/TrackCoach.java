package com.neimerc.springcoredemo.common;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

// No olvidar indicar, usando la anotación @Component, que esta clase es un bean Spring.
// Con la anotación @Lazy, este bean no se inicializará nada más comenzar la ejecución del programa.
@Component
@Lazy
public class TrackCoach implements Coach {

  // Se añade un diagnóstico para saber cuando se crea este bean
  public TrackCoach() {
    System.out.println("In constructor: " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Run a hard 5k!";
  }
  
}
