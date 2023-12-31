package com.jmunoz.aopdemo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

// Añadimos @Service para que Spring lo incluye en el escaneo de componentes.
// Recordar que los aspectos en Spring no funcionan si la clase no es un componente de Spring.
@Service
public class TrafficFortuneServiceImpl implements TrafficFortuneService {

  @Override
  public String getFortune() {

    // simulate a delay
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // return a fortune
    return "Expect heavy traffic this morning";
  }

}
