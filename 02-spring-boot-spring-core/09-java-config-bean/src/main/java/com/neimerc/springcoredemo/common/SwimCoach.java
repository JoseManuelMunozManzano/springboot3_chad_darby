package com.neimerc.springcoredemo.common;

// IMPORTANTE: NO se indica la anotación @Component
//      Es decir, NO es un bean de Spring.
// Lo convertirmos en un bean de Spring en la clase de configuración SportConfig.java
public class SwimCoach implements Coach {

  public SwimCoach() {
    System.out.println("In constructor: " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Swim 1000 meters as a warm up";
  }
  
}
