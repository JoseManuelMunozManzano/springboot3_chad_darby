# 10-spring-boot-aop

Tenemos esta arquitectura:

![alt text](./images/Architecture.png)

Si sobre esta arquitectura queremos añadir logging a nuestros métodos DAO, antes de comenzar el método, y nos dicen que probablemente haya que añadirlo en más sítios...

```
We don't cover logging in detail. In simple terms you can use this.

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {

        // display method we are calling
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====>> in @Before: calling method: " + theMethod);

        ...
   }
}

If you need more details on logging, here is a youtube tutorial

https://www.youtube.com/watch?v=UfdmMlMIKkg
```

Y si ahora necesitamos añadir código de seguridad a nuestro DAO, antes de ejecutarse...

Y si luego nos dicen que nos llevemos ese mismo logging y la seguridad a nuestro Service y al Controller...

Y luego que llevemos eso a todo el sistema...

![alt text](./images/TwoProblems.png)

## Otras posibles soluciones

- Herencia: Es posible que funcione, dependiendo de nuestro sistema, pero igualmente tendremos que extender de la clase base y tocar todo el código. Y si alguna de nuestras clases ya extiende de alguna, Java no soporta herencia múltiple. Esta solución NO va a funcionar

- Delegation: Las clases podrían delegar las llamadas a un gestor de logging y gestor de seguridad. Pero necesitamos igualmente actualizar nuestro código si queremos añadir ese soporte, o nuevas características como gestión de APIs... Esta solución tampoco funciona

- Aspect-Oriented Programming (AOP): Un aspecto encapsula lógica transversal. Es un código de infraestructura básico que necesitarán todas las areas de nuestra applicación. Lo que hacemos es tomar ese código de logging / seguridad, lo encapsulamos en un módulo o clase reutilizable y se puede aplicar en múltiples partes de nuestro proyecto de acuerdo a una configuración. Esta solución es buena

![alt text](./images/CrosssCuttingConcerns.png)

## Solución AOP

En una solución AOP, por debajo se aplica el patrón de diseño Proxy.

![alt text](./images/ProxyDesignPattern.png)

La app main no tiene ni idea sobre AOP, ni proxies. Solo tiene un método call. Es parecido a cuando hacemos una llamada por teléfono a un amigo. Por debajo, nuestra llamada está siendo monitoreada y nosotros no lo sabemos.

Beneficios de AOP:

- El código del aspecto está definido en una sola clase
  - Mucho mejor a que esté esparcido por todos lados
  - Promueve reutilización de código y es fácil de modificar
- El código de negocio en la app es más claro
  - Solo escribimos código específico de nuestro negocio
  - Reduce complejidad del código
- Configurable
  - Basado en configuración, aplicamos Aspectos selectivamente en diferentes partes de nuestra app
  - No es necesario hacer cambios al código principal de la app. Esto es muy importante!

Caso de uso de la AOP

- Más comunes:
  - logging, seguridad, transacciones
- Auditar logging
  - quién, qué, cuando, donde
- Manejo de excepciones
  - log de excepciones y notificación al equipo de DevOps via SMS/email
- Gestión API
  - ¿Cuántas veces se ha llamado a un método usuario?
  - Analytics: ¿horas punta?, ¿carga promedio? ¿mejor usuario?

![alt text](./images/ProsAndCons.png)

La unica advertencia sería no tener muchos Aspectos y que las operaciones no sean muy pesadas. Usarlo pero con moderación.

## Terminología AOP

- Aspect: Un código en un módulo para problemas transversales (logging, security...)
- Advice: Qué acción se toma y cuando debe aplicarse
- Join Point: Cuándo aplicar código durante la ejecución del programa
- Pointcut: Una expresión predicada para indicar dónde se deben aplicar los Advice

Tipos de Advice:

- Before advice: se ejecutan antes del método
- After finally advice: se ejecuta después del método (como en un finally, en un bloque try...catch)
- After returning advice: se ejecuta después del método (ejecución exitosa)
- After throwing advice: se ejecuta después del método (si se lanza una excepción)
- Around advice: se ejecuta antes y después del método

Weaving

- Conexión de aspectos a objetos de destino para crear un objeto advice
- Tipos diferentes de weaving:
  - Tiempo de compilación, tiempo de carga o tiempo de ejecución
- Con respecto al rendimiento: el weaving en tiempo de ejecución es el más lento

## Buenas prácticas: Aspects y Advices

- Que el código sean pocas líneas
- Que sea rápido
- No realizar operaciones pesadas o lentas
- Entrar y salir LO MÁS RÁPIDO POSIBLE

## Frameworks AOP

- Los dos Frameworks AOP líderes en Java son
  - Spring AOP
  - AspectJ

Spring AOP

- Spring provee soporte AOP de forma automática
- Ya lo usa por debajo en componentes clave
  - Seguridad, transacciones, caching...
- Usa weaving en tiempo de ejecución de aspectos, es decir, usa el patrón proxy para hacer el advice del objeto

![alt text](./images/RunTimeWeaving.png)

AspectJ

- Es el Framework AOP original, lanzado en 2001
- Provee soporte completo para AOP
- Soporte completo para:
  - join points: a nivel de método, constructor y campo
  - code weaving: en tiempo de compilación, post tiempo de compilación y tiempo de carga

Comparaciones

![alt text](./images/SpringAOPComparison.png)

![alt text](./images/AspectJComparison.png)

- Spring AOP es una implementación ligera de AOP, pero soluciona los problemas comunes en aplicaciones empresariales

Recomendación

- Comenzar con Spring AOP, porque es más fácil para comenzar
- Si tenemos requerimientos complejos, movernos a AspectJ
  - https://www.geekyhacker.com/how-to-configure-aspectj-in-spring-boot/

## 01-spring-boot-aop-demo

#### Ejemplo AOP con @Before Advice

![alt text](./images/AOPExample.png)

#### @Before Advice

![alt text](./images/BeforeAdvice.png)

#### Casos de uso

- Más comunes
  - logging, security, transactions (Spring ya hace esto por detrás, usando AOP con la anotación @Transactional)

#### Spring Boot AOP Starter

- Hay que añadir la dependencia spring-boot-starter-aop
  - Spring Boot habilita automáticamente el soporte para AOP
  - No es necesario usar de forma explícita @EnableAspectJAutoProxy, como antiguamente para Spring, ya lo tenemos por defecto para Spring Boot

#### Proceso de desarrollo

- Crear objeto objetivo: AccountDAO
  - Con la anotación @Component
- Crear main app
  - Será un programa de línea de comandos
- Crear Aspect con @Before advice
  - Anotaremos la clase con la anotación @Aspect y con la anotación @Component
  - Creamos un método y lo anotamos con @Before indicando el método de nuestro DAO (la expresión Pointcut)
    - @Before("execution(public void addAccount())")

![alt text](./images/BeforeExample.png)

#### Testeo

Para probar el aspecto, ejecutar la app.

Vemos que, por debajo, antes de ejecutarse addAccount() se ejecuta el aspecto beforeAddAccountAdvice()

#### AOP - Pointcut Expressions

Un Pointcut es una expresión predicada para indicar dónde se deben aplicar los Advice.

Hay varios tipos de pointcut.

- Spring AOP usa el lenguaje de pointcut expression de AspectJ
- Comenzaremos con pointcut `execution`
  - Se aplica a ejecución de métodos

**Coincidencia en el nombre del método o patrón**

![alt text](./images/PointcutExpressionLanguage.png)

![alt text](./images/PointcutExpressionLanguage_2.png)

![alt text](./images/PointcutExpressionLanguage_3.png)

![alt text](./images/PointcutExpressionLanguage_4.png)

![alt text](./images/PointcutExpressionLanguage_5.png)

![alt text](./images/PointcutExpressionLanguage_6.png)
