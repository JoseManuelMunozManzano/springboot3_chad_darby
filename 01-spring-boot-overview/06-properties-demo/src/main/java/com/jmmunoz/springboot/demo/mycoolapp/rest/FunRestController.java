package com.jmmunoz.springboot.demo.mycoolapp.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

  // Inyectar propiedades para : coach.name y team.name
  @Value("${coach.name}")
  private String coachName;

  @Value("${team.name}")
  private String teamName;

  // expone un nuevo endpoint para "teaminfo"
  @GetMapping("/teaminfo")
  public String getTeamInfo() {
    return "Coach: " + coachName + ", Team name: " + teamName;
  }
 
  // expone "/" que devuelve "Hello World!"  
  @GetMapping("/")
  public String sayHello() {
    return "Hello World!";
  }

  // expone un nuevo endpoint para "workout"
  @GetMapping("/workout")
  public String getDailyWorkout() {
    return "Run a hard 5K!";
  }

  // expone un nuevo endpoint para "fortune"
  @GetMapping("/fortune")
  public String getDailyFortune() {
    return "Today is you lucky day!";
  }
}
