package com.jmunoz.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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

  // add a new advice for @AfterReturning on the findAccounts method
  
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
