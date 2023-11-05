package com.neimerc.springcoredemo.common;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// No olvidar indicar, usando la anotación @Component, que esta clase es un bean Spring.
// Indicando la anotación @Primary, este es el bean que se va a inyectar en el controlador.
// Solo puede haber un @Primary para todas las clases que implementan Coach.
@Component
@Primary
public class TrackCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Run a hard 5k!";
  }
  
}
