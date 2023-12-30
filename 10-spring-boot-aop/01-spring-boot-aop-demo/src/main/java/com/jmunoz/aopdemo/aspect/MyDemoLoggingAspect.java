package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

  // this is where we add all of our related advices for logging

  // let's start with a @Before advice
  
  // La pointcut expression es: execution(public void addAccount())
  // Y lo que queremos indicar es: Ejecuta este código ANTES del método 
  // objeto destino: "public void addAccount()" encontrado EN CUALQUIER CLASE
  @Before("execution(public void addAccount())")
  public void beforeAddAccountAdvice() {
    System.out.println("\n=====>>> Executing @Before advice on addAccount()");
  }
}
