package com.neimerc.springcoredemo.common;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// Con la anotación @Scope indicamos el alcance del bean.
// Por defecto, sin indicar @Scope tenemos un scope de singleton (misma instancia para toda la app)
// En el ejemplo, tenemos un scope prototype (nueva instancia con cada inyección)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CricketCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Practice fast bowling for 15 minutes";
  }
  
}
