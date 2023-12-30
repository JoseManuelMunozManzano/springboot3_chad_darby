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

  // Crear un pointcut para los métodos getter
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.get*(..))")
  private void getter() {}

  // Crear un pointcut para los métodos setter
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.set*(..))")
  private void setter() {}
  
  // Crear un pointcut: incluir el paquete ... excluir getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  private void forDaoPackageNoGetterSetter() {}
  
  // Usando la declaración de pointcut
  @Before("forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice() {
    System.out.println("\n=====>>> Executing @Before advice on package com.jmunoz.aopdemo.dao(..)");
  }
  
  // Reutilizando la declaración de pointcut
  @Before("forDaoPackageNoGetterSetter()")
  public void performApiAnalytics() {
    System.out.println("\n=====>>> Performing API analytics");
  }

}
