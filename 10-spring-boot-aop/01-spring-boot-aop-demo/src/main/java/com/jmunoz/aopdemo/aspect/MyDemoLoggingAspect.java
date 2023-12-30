package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// Recordar que, para que funcionen los aspectos de Spring, tenemos que usar beans
// gestionados por Spring. Si creamos un objeto con el operador new, Spring no lo
// manejará y los aspectos de Spring no funcionarán.
@Aspect
@Component
public class MyDemoLoggingAspect {

  // this is where we add all of our related advices for logging

  // let's start with a @Before advice
  
  // La pointcut expression es: execution(public void addAccount())
  // Y lo que queremos indicar es: Ejecuta este código ANTES del método 
  // objeto destino: "public void addAccount()" encontrado EN CUALQUIER CLASE
  //
  // @Before("execution(public void addAccount())")
  // public void beforeAddAccountAdvice() {
  //   System.out.println("\n=====>>> Executing @Before advice on addAccount()");
  // }
  
  // Siendo muy específicos.
  // Cuando solo queremos que haga match por un método de una interface/clase en concreto,
  // tenemos que indicar el classname calificado => package + (class/interface)
  // En este ejemplo solo hará match para el método addAccount del paquete com.jmunoz.aopdemo.dao
  // y para la interface AccountDAO
  //
  // @Before("execution(public void com.jmunoz.aopdemo.dao.AccountDAO.addAccount())")
  // public void beforeAddAccountAdvice() {
  //   System.out.println("\n=====>>> Executing @Before advice on addAccount()");
  // }

  // Usando wildcards
  // Queremos que se haga match por cualquier método cuyo nombre comience por add
  //
  // @Before("execution(public void add*())")
  // public void beforeAddAccountAdvice() {
  //   System.out.println("\n=====>>> Executing @Before advice on addAccount()");
  // }
  
  // Hacer match en métodos dado un tipo de retorno. Se usa el wildcard.
  // También vemos que no se indica el modificador. Es opcional indicarlo, no hace falta el wildcard.
  //
  // @Before("execution(* add*())")
  // public void beforeAddAccountAdvice() {
  //   System.out.println("\n=====>>> Executing @Before advice on addAccount()");
  // }

  // Añadir un tipo de parámetro a la Pointcut expression.
  // IMPORTANTE: Indicamos el nombre de la clase totalmente calificado.
  @Before("execution(* add*(com.jmunoz.aopdemo.Account))")
  public void beforeAddAccountAdvice() {
    System.out.println("\n=====>>> Executing @Before advice on add*(Account)");
  }  
}
