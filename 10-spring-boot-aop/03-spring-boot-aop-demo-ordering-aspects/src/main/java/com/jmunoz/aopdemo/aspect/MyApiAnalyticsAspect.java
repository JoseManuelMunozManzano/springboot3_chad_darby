package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// En vez de indicar el nombre de clase calificado, también funciona 
// extender de la clase donde están los pointcut declarations.
@Aspect
@Component
@Order(3)
public class MyApiAnalyticsAspect extends LuvAopDeclarations {

  @Before("forDaoPackageNoGetterSetter()")
  public void performApiAnalytics() {
    System.out.println("\n=====>>> Performing API analytics");
  }

}
