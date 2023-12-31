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

**Coincidencia en los parámetros de los métodos**

Wildcards:

- () --> busca coincidencias con métodos que no tienen argumentos
- (\*) --> busca coincidencias con métodos que tienen un argumento de cualquier tipo
- (..) --> busca coincidencias con métodos que tienen 0 o más argumentos de cualquier tipo

![alt text](./images/PointcutExpressionLanguage_7.png)

![alt text](./images/PointcutExpressionLanguage_8.png)

![alt text](./images/PointcutExpressionLanguage_9.png)

**Coincidencia en el paquete**

![alt text](./images/PointcutExpressionLanguage_10.png)

## 02-spring-boot-aop-demo-pointcut-declarations

#### Declaraciones de Pointcut

Ahora mismo tenemos un problema. ¿Cómo podemos reutilizar una expresión Pointcut?

Soluciones:

- Copiar/pegar del método. No es la solución ideal
- La solución ideal es crear una declaración de pointcut y aplicarla a muchos advices
  - Usamos la anotación @Pointcut e indicamos la expresión
  - El método no tiene ni parámetros ni cuerpo, solo sirve para refeerenciar este pointcut
  - ![alt text](./images/PointcutDeclaration.png)
  - Ahora aplicamos este pointcut a nuestros advices, usando el nombre del método. Puede reutilizarse
  - ![alt text](./images/PointcutDeclaration_2.png)

Beneficios de usar declaraciones de Pointcut

- Facilmente reutilizables en expresiones pointcut
- Solo hay que actualizar el pointcut en un sitio
- Se pueden compartir y combinar expresiones pointcut

#### Proceso de desarrollo

- Crear una declaración de pointcut
- Aplicar la declaración de pointcut a los advices

#### Testeo

Para probar el aspecto, ejecutar la app.

#### Combinar Pointcuts

Tenemos un problema. ¿Cómo podemos aplicar varias expresiones pointcut a un advice?

Sería bueno ejecutar un advice solo si se reunen ciertas condiciones.

Queremos realizar el siguiente ejemplo: Aplicar la declaración de pointcut a todos los métodos de un paquete salvo a los métodos getter/setter

Combinando expresiones pointcut:

- Se pueden usar operadores lógicos para incluir/excluir métodos de la expresión pointcut
  - && -> AND
  - || -> OR
  - ! -> NOT
- Funciona como una sentencia "if"
- La ejecución ocurre solo cuando se evalúa a true

![alt text](./images/CombiningPointcuts.png)

#### Proceso de desarrollo

- Crear declaraciones de pointcut
- Combinar declaraciones de pointcut
- Aplicar la declaración de pointcut a advice(s)

#### Testeo

Para probar el aspecto, ejecutar la app.

## 03-spring-boot-aop-demo-ordering-aspects

#### Ordenando Aspects

Tenemos un problema. ¿Cómo controlamos el orden en el que se aplican los advices?

Vamos a tener tres advices:

- beforeAddAccountAdvice
- performApiAnalyticsAdvice
- logToCloudAdvice

¿Cómo gestionamos el orden de ejecución de los advices? Ahora mismo el orden es indefinido.

Proceso de desarrollo para controlar el orden:

- Refactor: Situar los advices en Aspects separados
  - Igual para los Pointcuts. Nos los llevamos a una clase separada para poderlos compartir
- Controlar el orden en los Aspects usando la anotación de clase @Order
  - Ejemplo: @Order(1)
  - Los números más bajos tiene una precedencia más alta
    - Rango: Integer.MIN_VALUE a Integer.MAX_VALUE
    - Los números negativos están permitidos
    - Los números no tienen por qué ser consecutivos
    - Si dos aspectos tienen el mismo número de @Order, su orden es indeterminado, pero no afecta al orden general de todos los aspectos
- Esto garantiza el orden en el que se aplicarán los Aspects

#### Testeo

Para probar el aspecto, ejecutar la app.

Veremos que el orden es:

- 1. MyCloudLogAspect
- 2. MyLoggingDemoAspect
- 3. MyApiAnalyticsAspect

![alt text](./images/OrderAnnotation.png)

#### Leer argumentos de los métodos con JoinPoints

Tenemos un problema. Cuando estamos en el aspecto, ¿Cómo podemos acceder a los parámetros de los métodos?

Proceso de desarrollo:

- Acceder y mostrar la firma del método
  - Usamos como argumento del método anotado con @Before un tipo JoinPoint
  - JoinPoint tiene metadata sobre el método que se está ejecutando
  - ![alt text](./images/MethodSignature.png)
- Acceder y mostrar los argumentos del método
  - También usamos como argumento del método anotado con @Before un tipo JoinPoint
  - ![alt text](./images/MethodArguments.png)

#### Ejemplo AOP con @AfterReturning Advice

Vamos a crear un advice que se ejecutará tras finalizar un método de forma exitosa.

El método sobre el que se aplicará el advice será findAccounts()

El advice @AfterReturning se ejecuta tras ejecutarse el método de forma exitosa (sin excepciones).

![alt text](./images/AfterReturningAdvice.png)

Normalmente necesitamos acceder al valor devuelto por el método llamado. En nuestro ejemplo findAccounts() devuelve una lista de Account y en nuestro advice los vamos a listar.

Para ello:

- Usamos el parámetro returning dentro de la anotación @AfterReturning
  - Le damos un nombre (result)
- Usamos un parámetro del método advice con tipo JoinPoint
- Usamos un tipo de parámetro en el método advice, que será en este caso la lista de Accounts
  - El nombre del parámetro será el indicado en returning (result)
- Ya dentro del método advice, podemos usar la variable (result)

![alt text](./images/AccessReturnValue.png)

Casos de uso:

- Más comunes
  - logging, security, transactions
- Post-procesamiento de datos
  - Post-procesamiento de datos antes de volver al llamador
  - Formatear o enriquecer la data (con cuidado)

Proceso de desarrollo:

- Trabajo preparatorio: Añadir constructores a la clase Account
- Añadir un nuevo método: findAccounts() en AccountDAO
- Actualizar la app main par que llame al nuevo método: findAccounts()
- Añadir un advice @AfterReturning

Test:

- Ejecutar la app y ver el resultado en consola.

#### @AfterReturning Advice - Modificar valor devuelto

Nos concentramos en el uso de caso en el que realizamos un post-procesamiento de la data antes de devolverla al llamador.

Interceptamos la data y realizamos un trabajo adicional sobre ella, formateándola o enriqueciéndola.

Eso si, tenemos que tener cuidado porque esa data transformada, un programador que no haya hecho ese advice o no conozca AOP, puede que no sepa de donde viene.

El equipo de desarrollo debe ser comunicado de que se está usando AOP y que se está haciendo esta transformación en la data.

Recordar que usar AOP es como que hay un espía. Es como si se hace un pedido a Amazon y ese espía coge el paquete y le pone o quita cosas.

El paquete nos llega y no hay lo que habíamos pedido. Nosotros no sabemos que existe ese espía y que ese paquete fue interceptado y modificado.

![alt text](./images/ModifyReturnValue.png)

![alt text](./images/CallingProgram.png)

Test:

- Ejecutar la app y ver el resultado en consola.

#### Ejemplo AOP con @AfterThrowing Advice

El advice @AfterThrowing se ejecuta tras finalizar un método si se lanza una excepción.

![alt text](./images/AfterThrowing.png)

Una vez que se lanza la excepción podemos leerla. La excepción sigue propagándose hacia atrás hasta la app main.

![alt text](./images/AfterThrowingSequenceDiagram.png)

Casos de uso:

- Log de la excepción
- Hacer auditoría de la excepción
- Notificar al equipo de DevOps por email o SMS
  - Evitar mandar SMS por cada excepción para no hacer spam. Solo para cosas muy críticas
- Encapsular esta funcionalidad en aspectos AOP para una fácil reutilización
  - En caso de excepción, se ejecutará esa funcionalidad

Vamos a hacer un ejemplo en el que creamos un advice que se ejecutará tras lanzarse una excepción.

Para ello:

- Necesitaremos acceder al objeto de la excepción
  - Usamos el parámetro throwing dentro de la anotación @AfterThrowing
  - Le damos un nombre (theExc)
- Usamos un parámetro del método advice con tipo JoinPoint
- Usamos un tipo de parámetro en el método advice, que será un Throwable
  - El nombre del parámetro será el indicado en returning (theExc)
- Ya dentro del método advice, podemos usar la variable (theExc)

![alt text](./images/AfterThrowingExample.png)

Propagación de la excepción

- Hasta este momento, solo hemos interceptado (leido) la excepción
- Sin embargo, la excepción se sigue propagando al programa llamador
  - La excepción funciona hasta ahora exactamente igual que si no existiera advice
- Pero si queremos parar la propagación de la excepción (lo vemos más adelante)
  - Usaremos el advice @Around

Proceso de desarrollo:

- En nuestra app main, añadir un bloque try/catch para manejar la excepción
- Actualizar AccoundDAO para simular el lanzamiento de una excepción
- Añadir un advice @AfterThrowing

Test:

- Ejecutar la app y ver el resultado en consola.

#### Ejemplo AOP con @After Advice

El advice @After se ejecuta tras finalizar un método.

Funciona parecido a un finally en un bloque try/catch/finally, en el sentido en que, independientemente de que finalice exitosamente o con una excepción, se ejecutará este advice.

![alt text](./images/AfterAdvice.png)

![alt text](./images/AfterAdviceSequenceDiagram.png)

Casos de uso:

- Log de la excepción y/o realizar una auditoría
- Código a ejecutar independientemente del resultado del método
  - Por ejemplo, para hacer limpieza de recursos
- Encapsular esta funcionalidad es aspectos AOP para una fácil reutilización en diferentes partes de nuestra app

Vamos a hacer un ejemplo en el que creamos un advice que se ejecutará tras el método (finally ... éxito/error)

![alt text](./images/AfterAdviceExample.png)

Trucos para el advice @After

- El advice @After no tiene acceso a la excepción
  - Si necesitamos la excepción, usaremos el advice @AfterThrowing
- El advice @After debería poder ejecutarse en caso de éxito o error
  - El código no debe depender del camino feliz o de una excepción
  - Logging / auditoría es aquí un ejemplo típico. Escribimos qué ha pasado, no intentamos inspeccionar o hacer algo muy elaborado

Proceso de desarrollo:

- Añadir un advice @After
- Probar el caso de fallo/excepción
- Probar el caso de éxito

Test:

- Ejecutar la app y ver el resultado en consola.

#### Ejemplo AOP con @Around Advice

El advice @Around se ejecuta antes y después de la ejecución del método.

![alt text](./images/AroundAdvice.png)

Casos de uso:

- Más común: logging, auditoría, seguridad
- Pre-procesamiento y post-procesamiento de la data
- Instrumentar / Perfilar código
  - ¿Cúanto tarda en ejecutarse una sección de código?
- Gestionar excepciones
  - Pasar / manegar / parar excepciones

Vamos a hacer un ejemplo con nuestro antiguo FortuneService que hemos usado en otros módulos del curso.

Queremos saber cuanto tarda una sección del código en ejecutarse.

![alt text](./images/AroundAdviceSequenceDiagram.png)

Para ello:

- Cuando usamos el advice @Around necesitamos un manejador del método de destino
  - A esto se le llama obtener una referencia a un "proceeding join point"
  - Necesitamos en el método, por tanto, un parámetro de tipo ProceedingJoinPoint
- En nuestro código usamos este proceeding join point para ejecutar el método de destino
  - Usamos el método proceed() para ejecutar el método de destino. Se devuelve un Object con el resultado
- Al final del método advice devolvemos el resultado al método llamador

![alt text](./images/AroundAdviceExample.png)

Proceso de desarrollo:

- Crear un servicio TrafficFortuneService
- Actualizar la app main para llamar a TrafficFortuneService
- Añadir un advice @Around

Test:

- Ejecutar la app y ver el resultado en consola.

#### @Around Advice - Manejo de excepciones

Para una excepción lanzada desde el proceeding join point:

- Podemos manejarla/pasar de/parar la excepción
- O podemos relanzar la excepción

Tenemos un control muy fino sobre como podemos llamar al método de destino.

Tanto si manejamos o paramos la excepción, el programa llamador NUNCA va a conocer que se lanzó una excepción, porque la estamos gestionando.

Podemos gestionar/parar la excepción y devolver un valor por defecto o relanzar la excepción y que esta vuelva al programa llamador.

Realmente depende de la aplicación, pero siempre que se pueda solucionar el problema raiz, evitando que se generen las excepciones, será lo mejor.

Hay que usar esta característica con mucho cuidado!

![alt text](./images/AroundAdviceHandleExceptionExample.png)

Proceso de desarrollo:

- Añadir una bandera para simular una excepción
- Modificar el advice @Around para manejar la excepción

Test:

- Ejecutar la app y ver el resultado en consola.

#### @Around Advice - Relanzar la excepción

Nos centramos en como podemos relanzar la excepción desde un advice @Around.

![alt text](./images/AroundAdviceRethrowExceptionExample.png)

Proceso de desarrollo:

- Modificar el advice @Around para relanzar la excepción

Test:

- Ejecutar la app y ver el resultado en consola.

## 04-spring-boot-mvc-crud-demo-employees

El objetivo de este proyecto es añadir soporte de logging AOP a una app Spring MVC CRUD.

![alt text](./images/AOPWithSpringMVCDiagram.png)

Proceso de desarrollo:

- Añadir Spring Boot AOP Starter al fichero Maven pom
  - Una vez hecho esto Spring Boot habilitará automáticamente el soporte para AOP
- Crear Aspect
  - Añadir soporte de logging
  - Configurar pointcut declarations
    - ![alt text](./images/AOPWithSpringMVCPointcutDeclarations.png)
  - Añadir advice @Before
  - Añadir advice @AfterReturning

Para el proyecto se usa MariaDB y hay que ejecutar los SQL del siguiente script: `./04-spring-boot-mvc-crud-demo-employees/sql-scripts/employee-directory.sql` en SQuirreL.

Nota: Yo he usado una imagen de Docker de MariaDB. El comando que he utilizado ha sido el siguiente:

```
  docker container run \
  -e MARIADB_USER=springstudent \
  -e MARIADB_PASSWORD=springstudent \
  -e MARIADB_ROOT_PASSWORD=springstudentroot \
  -e MARIADB_DATABASE=student_tracker \
  -dp 3306:3306 \
  --name student_tracker \
  --volume student_tracker:/var/lib/mysql \
  mariadb:jammy
```

Y para gestionar la BBDD uso el programa SQuirreL.

Realizar los siguientes SQLs para ver los datos:

```
USE `employee_directory`;
select * from employee;
```

Acceder a la siguiente URL: `http://localhost:8080`

Jugar con la web app y observar la consola para ver los resultados de los advice.
