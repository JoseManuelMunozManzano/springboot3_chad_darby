package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// Recordar que, para que funcionen los aspectos de Spring, tenemos que usar beans
// gestionados por Spring. Si creamos un objeto con el operador new, Spring no lo
// manejará y los aspectos de Spring no funcionarán.
//
// Indicamos el orden de ejecución de los advices
@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

  // Tenemos que indicar el nombre de clase calificado porque ahora los pointcut declarations están en su propia clase.
  @Before("com.jmunoz.aopdemo.aspect.LuvAopDeclarations.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice() {
    System.out.println("\n=====>>> Executing @Before advice on package com.jmunoz.aopdemo.dao(..)");
  }

}
