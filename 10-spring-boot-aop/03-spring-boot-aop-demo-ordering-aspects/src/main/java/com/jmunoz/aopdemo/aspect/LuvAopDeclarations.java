package com.jmunoz.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

// Si solo tenemos pointcuts la anotación @Aspect es opcional.
// Solo es obligatoria si se añaden advices (@Before, @After...)
// La añadimos solo por si en el futuro fuera necesario añadir advices.
@Aspect
public class LuvAopDeclarations {

  // Creando una declaración de pointcut.
  // El método no tiene ni parámetros ni código.
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.*(..))")
  public void forDaoPackage() {}

  // Crear un pointcut para los métodos getter
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.get*(..))")
  public void getter() {}

  // Crear un pointcut para los métodos setter
  @Pointcut("execution(* com.jmunoz.aopdemo.dao.*.set*(..))")
  public void setter() {}
  
  // Crear un pointcut: incluir el paquete ... excluir getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  public void forDaoPackageNoGetterSetter() {}

}
