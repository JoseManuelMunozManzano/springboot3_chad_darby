# Spring Boot 3, Spring 6 & Hibernate for Beginners

Del curso Udemy: `https://www.udemy.com/course/spring-hibernate-tutorial/`

Curso realizado usando JDK17 y VSCode

## 01-spring-boot-overview

### 01-spring-boot-demo

Primera aplicación para ir aprendiendo a usar Spring Boot 3.

Temas a tratar:

- Creación de un REST Controller muy simple que devuelve: Hello World!
- Añadir properties al fichero application.properties y usarlo en nuestro controller
- Añadir devtools

### 02-dev-tools-demo

Otro ejemplo con las devtools añadidas.

- Se añaden más endpoints estando la aplicación iniciada para ver que las devtools funcionan.

### 03-actuator-demo

Ejemplo con Spring Boot Starter Actuator, para monitorizar y gestionar la aplicación.

- Se han añadido a las properties los endpoints /health (ya viene por defecto) y /info
- Los endpoints son: `http://localhost:8080/actuator/health` y `http://localhost:8080/actuator/info`
- Properties con la configuración de actuator/info
- Se exponen más endpoints de actuator: actuator/beans, actuator/threaddump, actuator/mappings... hasta un total de 13.

### 04-actuator-security-demo

Si queremos exponer los endpoints de actuator, pero a la vez tener implementada la seguridad para evitar que cualquiera pueda ver esa información.

- Añadido al POM la dependencia spring-boot-starter-security
- Esto hace que al ingresar en, por ejemplo, /actuator/mappings, aparezca la seguridad: /login
- El username por defecto es user y el password aparece en los logs de ejecución de Spring Boot, en la consola
- Añadido al fichero properties la deshabilitación de algunos endpoints de actuator (queda comentado)
- Añadido al fichero properties una configuración personalizada de username y password de seguridad

### 05-command-line-demo

Este proyecto no tiene nada de especial y no hace falta ni analizarlo.

Si está aquí es simplemente para demostrar que se puede ejecutar un proyecto Spring desde la terminal, sin necesidad de utilizar el IDE.

Para ello:

- **EJECUCIÓN CON MAVEN**
- En una terminal acceder a la carpeta de este proyecto.
- Crear el archivo .jar de la aplicación con el comando `./mvnw package`
- NOTA: Si tenemos Maven instalado, se puede ejecutar el comando `mvn package`
- Si todo va bien, se habrá creado nuestro archivo .jar en el subdirectorio target
- Accedemos al subdirectorio target: `cd target`
- Ahora podemos ejecutar nuestra aplicación con el siguiente comando: `java -jar <nombre_fichero_jar>`
- Vamos al navegador a las siguientes rutas para comprobar que funciona: `http://localhost:8080/`, `http://localhost:8080/workout`, `http://localhost:8080/fortune`
- Cancelar la ejecución de la aplicación en el terminal pulsando Ctrl+C

- **EJECUCIÓN CON SPRING BOOT MAVEN PLUGIN**
- En una terminal acceder a la carpeta de este proyecto.
- Ejecutar el siguiente comando: `./mvnw spring-boot:run`
- Vamos al navegador a las siguientes rutas para comprobar que funciona: `http://localhost:8080/`, `http://localhost:8080/workout`, `http://localhost:8080/fortune`
- Cancelar la ejecución de la aplicación en el terminal pulsando Ctrl+C

### 06-properties-demo

De nuevo un ejemplo usando el fichero application.properties

- Añadimos nuestras propiedades personalizadas al fichero applicaction.properties. Ejemplo: `coach.name=Mickey Mouse`
- Si se indican que esas propiedades son desconocidas, en VSCode, pulsando Cmd+. podemos añadirlas a las metadata
- En nuestro controller inyectamos las propiedades usando la anotación @Value, por ejemplo:

  ```
    @Value("${coach.name}")
    private String coachName;
  ```

- Exponemos un nuevo endpoint:

  ```
    @GetMapping("/teaminfo")
    public String getTeamInfo() {
      return "Coach: " + coachName + ", Team name: " + teamName;
    }
  ```

- Probar la siguiente ruta: `http://localhost:8080/teaminfo` (ya no funcionará esta ruta. Seguir leyendo)

Vamos a realizar aquí también configuraciones a Spring Boot a través del fichero de properties.

Para ver todas las properties disponibles:

`https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties`

- Modificar el puerto por defecto del servidor: `server.port=7070`
- Modificar el path de contexto de nuestra aplicación: `server.servlet.context-path=/mycoolapp`
- Al ejecutar el proyecto aparecerá algo del tipo: `Tomcat started on port(s): 7070 (http) with context path '/mycoolapp'`
- Probar la siguiente ruta: `http://localhost:7070/mycoolapp/teaminfo`

## 02-spring-boot-spring-core

Páginas web:

```
  https://www.vojtechruzicka.com/field-dependency-injection-considered-harmful/
```

### 01-constructor-injection

Proyecto donde vamos a ver un ejemplo de Inversión de Control (IoC) e Inyección de Dependencias usando el constructor para inyectar las dependencias.

Que veremos:

- Definición de interfaz de dependencia y clase
  - Se usa la anotación @Component en la clase para marcarla como un bean de Spring
- Crear un REST Controller
  - Se usa la anotación @RestController
  - Crear un constructor para realizar la inyección de dependencias
  - Se usa la anotación @Autowired en el constructor, pero si solo tenemos un constructor, esta anotación es opcional
  - Añadir un endpoint: @GetMapping("/dailyworkout")
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 02-component-scanning

Proyecto donde vamos a ver un ejemplo de escaneo de componentes en distintos packages.

- Se ha creado una serie de packages (rest y common) dentro del package springcoredemo
  - Dentro del package springcoredemo los componentes son escaneados automáticamente por Spring Boot
- Se ha creado un package llamado util fuera del package springcoredemo
  - Si llevo los fuentes de springcoredemo/common a /util, veo que no se realiza el escaneo de componentes y falla
  - Para que Spring Boot escanee el package util hay que indicarselo de la siguiente forma:
    ```
      @SpringBootApplication(
      	scanBasePackages = {"com.neimerc.springcoredemo", "com.neimerc.util"}
      )
    ```

### 03-setter-injection

Proyecto donde vamos a ver un ejemplo de Inversión de Control (IoC) e Inyección de Dependencias usando el método setter para inyectar las dependencias.

Que veremos:

- Crear un método setter en la clase controller para realizar la inyección
- Configurar la inyección de dependencia usando la anotación @Autowired
- Indicando @Autowired, se puede inyectar una dependencia en cualquier método, no necesariamente un setter
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 04-qualifiers

Proyecto donde vemos como gestionar la inyección de dependencias cuando hay varias implementaciones de una interface.

- Veremos la anotación @Qualifier para ser específico sobre que bean inyectar
- Veremos esta anotación tanto para inyección usando el constructor como usando un setter
- La forma de usarla es: `@Qualifier("miClase")` donde el primer carácter (m) es en minúsculas
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 05-primary

Proyecto donde vemos una solución alternativa para gestionar la inyección de dependencias cuando hay varias implementaciones de una interface.

- Cuando no nos importa que bean se va a inyectar, cogemos la que tenga la anotación @Primary
- Solo una clase de las que implementan la interface puede tener la anotación @Primary
- Con esto, se hace innecesario usar la anotación @Qualifier en el controlador
- Es posible mezclar el uso de @Qualifier y @Primary, solo hay que tener en cuenta que @Qualifier tiene prioridad
- En general, se recomienda usar @Qualifier porque es más específica sobre que bean se va a inyectar
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 06-lazy-initialization

Proyecto donde vemos como hacer que los beans no se inicialicen al ejecutarse la aplicación.

- Por defecto, al comenzar la aplicación, todos los beans son inicializados
- Es decir, Spring crea una instancia de cada uno de ellos (anotados con @Component...) para que estén disponibles
- Probaremos que se inician usando getClass().getSimpleName() en el constructor de cada implementación de la interface Coach
- Usando lazy initialization, un bean se inicializará solo en los siguientes casos:
  - Si se necesita por una inyección de dependencia
  - Si es requerido explicitamente
- Un bean tendrá lazy initialization si se usa la anotación @Lazy en la implementanción del bean
- Como puede haber muchas clases, se puede indicar lazy initialization como una propiedad de configuración global
  - spring.main.lazy-initialization=true
  - Todas las clases, incluido nuestro controlador (tiene la anotación @RestController) son lazy
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 07-bean-scopes

Proyecto donde vemos el ciclo de vida de un bean, cuánto vive un bean, cuántas intancias se crean y como se comparte.

- Podemos cambiar el scope en cada una de las clases (beans) que implementa la interface Coach de la siguiente forma:
- El scope por defecto es singleton. Crea una instancia compartida
  - @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
- El scope prototype crea una nueva instancia de bean en cada inyección realizada
  - @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  - IMPORTANTE: Los bean con scope prototype son lazy por defecto. No hace falta usar @Lazy
- El scope request se usa solo para aplicaciones web. Alcance a una solicitud web HTTP
- El scope session se usa solo para aplicaciones web. Alcance a una sesión web HTTP
- El scope global-session se usa solo para aplicaciones web. Alcance a una sesión web HTTP global
- Testear en la siguiente ruta: `http://localhost:8080/check`

### 08-bean-lifecycle-methods

Proyecto donde vemos como añadir código personalizado durante la inicialización de un bean y durante su destrucción.

- Podemos añadir código para:
  - Llamar a métodos personalizados que contengan lógica de negocio
  - Configuraciones/Limpieza de recursos tales como db, sockets, ficheros...
- Para añadir este código, en las clases que implementan la interface Coach se añade la anotación:
  - @PostConstruct seguida del método que contiene la lógica de lo que se quiera hacer después de crearse el bean
  - @PreDestroy seguida del método que contiene la lógica de lo que se quiera hacer antes de destruirse el bean
- Testear en la consola de Spring al arrancar y parar el proyecto
- IMPORTANTE: Para un bean con scope prototype, Spring no llama al método destroy
