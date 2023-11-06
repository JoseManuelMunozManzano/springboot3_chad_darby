package com.neimerc.springcoredemo.common;

import org.springframework.stereotype.Component;

// No olvidar indicar, usando la anotación @Component, que esta clase es un bean Spring.
@Component
public class TrackCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Run a hard 5k!";
  }
  
}
