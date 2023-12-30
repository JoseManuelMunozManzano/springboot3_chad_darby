package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// Recordar que, para que funcionen los aspectos de Spring, tenemos que usar beans
// gestionados por Spring. Si creamos un objeto con el operador new, Spring no lo
// manejará y los aspectos de Spring no funcionarán.
@Aspect
@Component
public class MyDemoLoggingAspect {

  // this is where we add all of our related advices for logging

  // Creando una declaración de pointcut.
  // El método no tiene ni parámetros ni código.
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.*(..))")
  private void forDaoPackage() {}
  
  // Usando la declaración de pointcut
  @Before("forDaoPackage()")
  public void beforeAddAccountAdvice() {
    System.out.println("\n=====>>> Executing @Before advice on package com.jmunoz.aopdemo.dao(..)");
  }
}
