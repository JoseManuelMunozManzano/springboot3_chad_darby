package com.neimerc.springcoredemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.neimerc.springcoredemo.common.Coach;
import com.neimerc.springcoredemo.common.SwimCoach;

// Importante indicar la anotación @Configuration para indicar a Spring que aquí
// vamos crear beans de Spring.
@Configuration
public class SportConfig {

  // definimos métodos @Bean para configurar los beans de Spring
  // El id del bean por defecto es el nombre del método: swimCoach
  // Esto es por si tenemos que inyectarlo usando la anotación @Qualifier
  //
  // Pero si queremos personalizar el id del bean, se puede hacer, como en este ejemplo
  // donde se ha dado el id: aquatic
  // Y para inyectarlo se usa @Qualifier("aquatic")
  @Bean("aquatic")
  public Coach swimCoach() {
    return new SwimCoach();
  }
  
}
