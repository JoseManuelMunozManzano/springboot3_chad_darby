package com.neimerc.springcoredemo.common;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class CricketCoach implements Coach {

  // definir método init
  @PostConstruct
  public void doMyStartupStuff() {
    System.out.println("In doMyStartupStuff(): " + getClass().getSimpleName());
  }

  // definir método destroy
  @PreDestroy
  public void doMyCleanupStuff() {
    System.out.println("In doMyCleanupStuff(): " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Practice fast bowling for 15 minutes";
  }
  
}
