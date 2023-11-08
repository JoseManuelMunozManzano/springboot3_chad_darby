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

### 09-java-config-bean

Proyecto donde, en vez de usar la anotación @Component (u otra) para indicar que es un bean de Spring, se configura via código Java.

- Hay que crear una clase de configuración e indicar la anotación @Configuration
- Se crean métodos de creación de instancia a los que se le indica la anotación @Bean
  - Si llamamos al método swimCoach entonces el id del bean, por defecto, será swimCoach
  - Se puede cambiar el id del bean indicándolo en la misma anotación @Bean, por ejemplo: @Bean("aquatic")
- Ya podemos inyectar el bean en nuestro controlador usando, si fuera necesario, la anotación @Qualifier y el id del bean
  - @Qualifer("swimCoach") o @Qualifier("aquatic"), dependiendo del id del bean que hayamos indicado

¿Cuándo se usan estas clases de configuración de beans de Spring?

- Cuando queremos hacer disponible para Spring una clase de terceros, ya que no tenemos acceso al código fuente

- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout` y también mirando la consola de Spring

## 03-spring-boot-hibernate-jpa-crud

Hibernate maneja todo el SQL de bajo nivel y minimiza la cantidad de código JDBC que tenemos que desarrollar.

Hibernate también ofrece ORM (Object-to-Relational Mapping)

El desarrollador define el mapeo entre la clase de Java y la tabla en BBDD. Este mapeo se configura via fichero XML o anotaciones.

JPA (Jakarta Persistence API) es una especificación, la API estándar para ORM.

Define un conjunto de interfaces y requiere una implementación para ser usada (Hibernate es una implementación de JPA)

Los beneficios de usar JPA es que no estamos limitados por la implementación de un proveedor y podemos mantener un código flexible y portable implementando las interfaces de JPA.

El cambio de un proveedor a otro conllevará un mínimo de cambio de código porque estamos escribiendo código para la implementación estandar.

Hibernate / JPA usa JDBC para todas las comunicaciones de BBDD, es decir, Hibernate / JPA es otra capa de abstracción por encima de JDBC.

- `EntityManager` es un componente especial de JPA que se usa para crear queries...
- Basado en las configuraciones, Spring Boot creará automáticamente los beans: DataSource, EntityManager...
- Podremos entonces inyectarlas en la app, por ejemplo en nuestros DAO
- Para guardar en BD: `entityManager.persist(mi_objeto);`
- Para recuperar de BD usando la primary key: `Student myStudent = entityManager.find(Student.class, theID)`
- Hay otras formas de recuperar objetos de BD...

### 01-cruddemo-student

Para el proyecto se usa MariaDB y hay que ejecutar para crear el usuario y las tablas de trabajo los siguientes scripts, que se encuentran en la carpeta 00-starter-sql-scripts:

- 01-create-user.sql
- 02-student-tracker.sql

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

Para ver la información que vayamos guardando en esta app ejecutar: `select * from student_tracker.student;`

- Configurar el proyecto con Spring Initialzr (yo uso VSCode para ello)
- Añadir dependencias al POM:
  - MariaDB Driver: `mariadb-java-client`
  - Spring Data JPA: `spring-boot-starter-data-jpa`
- Añadir la configuración de conexión a BD en el fichero `application.properties`
  ```
    spring.datasource.url=jdbc:mariadb://localhost:3306/student_tracker
    spring.datasource.username=springstudent
    spring.datasource.password=springstudent
  ```
- No hace falta indicar el nombre del driver JDBC porque Spring Boot lo detectará automáticamente, basado en la URL

Para empezar sencillo, y poder centrarnos en Hibernate/JPA, se hará una app de línea de comandos con Spring Boot.
Para ello:

```
  @Bean
  public CommandLineRunner commandLineRunner(String[] args) {
    return runner -> {
      System.out.println("Hello World!");
    };
  }
```

Este método se crea en nuestro fuente main y se ejecuta tras haberse cargado nuestros Spring Beans.

- Para probar, ejecutar el proyecto y se debe ver el texto Hello World! en la terminal de ejecución

Para quitar el banner de Spring Boot y reducir el nivel de logging para mostrar solo warnings y errores (esto es porque es una app muy pequeña donde queremos ver el resultado en el terminal), en el fichero application.properties añadir lo siguiente:

```
  spring.main.banner-mode=off
  logging.level.root=warn
```

**Proceso de Desarrollo JPA**

1. Anotar Clase Java
2. Desarrollar código Java para realizar operaciones de BD

- Entity Class: Clase Java que está mapeada a una tabla de BD. Usamos ORM (Object-to-Relational Mapping)
  - Debe ser anotada con @Entity
  - Debe tener un constructor public o protected sin argumentos. La clase puede tener otros constructores
- Con respecto a las anotaciones Java
  - Mapeamos la clase a una tabla de BD
  - Mapeamos los campos a columnas de la BD

Ejemplo:

```
  @Entity
  @Table(name="student")
  public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    ...
  }
```

**NOTAS**
La anotación @Column es opcional. Si no se especifica, el nombre de la columna es el mismo que el del campo Java. Pero se recomienda indicarlo siempre.
Igual para la anotación @Table, si no se indica el nombre de la tabla en BD es el mismo que el de la clase. Mejor ser explícito e indicarlo.

Se indica @GeneratedValue(strategy=GenerationType.IDENTITY) para generar los id.
Si es necesaria una estrategia de generación de ids personalizada, también se puede. Hay que crear una implementación personalizada de la interface `org.hibernate.id.IdentifierGenerator` y hacer override al método `public Serializable generate(...)`

**Grabar un Objeto Java con JPA**

Vamos a hacer un CRUD application, es decir, Create, Read, Update y Delete.

Para ellos usaremos un DAO (Data Access Object) que es responsable de hacer de interfaz con la BBDD. Es un patrón de diseño muy común.

Crearemos métodos:

- save()
- findById()
- findAll()
- findByLastName()
- update()
- delete()
- deleteAll()

Nuestro DAO necesita un JPA Entity Manager, que es un componente principal para guardar/recuperar entidades.

Nuestro JPA Entity Manager necesita un Data Source. La Data Source define la información para la conexión con la BBDD.

Tanto el JPA Entity Manager como el Data Source son creados automáticamente por Spring Boot, basado en la información especificada en el fichero application.properties (JDBC URL, user id, password, ...)

Podemos inyectar (autowired) el JPA Entity Manager en nuestro Student DAO.

```
  Student DAO   <------>   Entity Manager   <------>   Data Source   <------>   BBDD
```

Para realizar todo esto, los pasos son:

- Definir nuestra interface DAO
- Definir la implementación del DAO, inyectando el Entity Manager
- Actualizar nuestra app main

Otras cosas necesarias son:

- Spring proporciona la anotación @Transactional. Automáticamente comienza y termina una transacción para el código JPA. No hay necesidad de hacerlo explicitamente en el código

Anotaciones especiales para DAO

- @Repository, que sería como una "sub-anotación" de @Component pero aplicada a implmentaciones DAO
  - Spring registrará automáticamente la implementación DAO gracias al escaneo de componentes
  - Spring también traducirá cualquier excepción checked de JDBC a excepciones unchecked

En cuanto a nuestro app main

- Inyectaremos StudentDAO en nuestro método commandLineRunner y crearemos otro método para crear un Student usando new, grabarlo y mostrarlo
