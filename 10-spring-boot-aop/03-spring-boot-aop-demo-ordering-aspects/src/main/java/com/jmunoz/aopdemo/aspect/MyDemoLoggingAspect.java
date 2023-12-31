package com.jmunoz.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jmunoz.aopdemo.Account;

// Recordar que, para que funcionen los aspectos de Spring, tenemos que usar beans
// gestionados por Spring. Si creamos un objeto con el operador new, Spring no lo
// manejará y los aspectos de Spring no funcionarán.
//
// Indicamos el orden de ejecución de los advices
@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

  // El advice @Around se ejecuta antes y después de ejecutarse el método de destino.
  // Es necesario indicar un tipo de parámetro ProceedingJoinPoint, que es un manejador del método de destino.
  @Around("execution(* com.jmunoz.aopdemo.service.*.getFortune(..))")
  public Object aroundGetFortune(ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {

    // print out method we are advising on
    String method = theProceedingJoinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @Around on method: " + method);

    // get begin timestamp
    long begin = System.currentTimeMillis();

    // now, let's execute the target method
    Object result = theProceedingJoinPoint.proceed();

    // get end timestamp
    long end = System.currentTimeMillis();

    // compute duration and display it
    long duration = end - begin;
    System.out.println("\n=====>>> Duration: " + duration / 1000.0 + " seconds");

    // Este result es el mismo donde hemos obtenido el resultado de ejecutar el método de destino.
    return result;
  }

  // El advice @After se ejecuta siempre, independientemente de una ejecución 
  // del método exitosa o con lanzamiento de excepción
  @After("execution(* com.jmunoz.aopdemo.dao.AccountDAO.findAccounts(..))")
  public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {

    // print out which method we are advising on
    String method = theJoinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @After (finally) on method: " + method);

  }

  // Recordar que el nombre indicado en throwing, debe ser el mismo que aparezca en el parámetro del método.
  @AfterThrowing(
        pointcut = "execution(* com.jmunoz.aopdemo.dao.AccountDAO.findAccounts(..))",
        throwing = "theExc")
  public void afterThrowingFindAccountsAdvice(JoinPoint theJoinPoint, Throwable theExc) {

    // print out which method we are advising on
    String method = theJoinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterThrowing on method: " + method);
    
    // log the exception
    System.out.println("\n=====>>> The exception is: " + theExc);
  }
  
  // Recordar que el nombre indicado en returning, debe ser el mismo que aparezca en el parámetro del método.
  @AfterReturning(
        pointcut = "execution(* com.jmunoz.aopdemo.dao.AccountDAO.findAccounts(..))",
        returning = "result")
  public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {

    // print out which method we are advising on
    String method = theJoinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);
    
    // print out the results of the method call
    System.out.println("\n=====>>> result is: " + result);
    
    // let's post-process the data ... let's modify it!
    
    // convert the account names to uppercase
    convertAccountNamesToUppercase(result);

    System.out.println("\n=====>>> result is: " + result);
  }

  private void convertAccountNamesToUppercase(List<Account> result) {

    // loop through accounts
    for (Account tempAccount : result) {
      
      // get uppercase version of name
      String theUpperName = tempAccount.getName().toUpperCase();

      // update the name on the account
      tempAccount.setName(theUpperName);
    }

  }

  // Tenemos que indicar el nombre de clase calificado porque ahora los pointcut declarations están en su propia clase.
  // JoinPoint tiene metadata sobre el método que se está ejecutando. Así podemos obtener la firma del método y sus argumentos.
  @Before("com.jmunoz.aopdemo.aspect.LuvAopDeclarations.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
    System.out.println("\n=====>>> Executing @Before advice on package com.jmunoz.aopdemo.dao(..)");

    // display the method signature
    MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
    
    System.out.println("Method: " + methodSignature);

    // display method arguments

    // get args
    Object[] args = theJoinPoint.getArgs();

    // loop thru args
    for (Object tempArg : args) {
      System.out.println(tempArg);

      if (tempArg instanceof Account) {
        
        // downcast and print Account specific stuff
        Account theAccount = (Account) tempArg;

        System.out.println("account name: " + theAccount.getName());
        System.out.println("account level: " + theAccount.getLevel());
      }
    }
  }

}
