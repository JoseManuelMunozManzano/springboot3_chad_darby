# 04-spring-boot-rest-crud

Veremos:

- Crear REST APIs / Web Services con Spring
- Discutir conceptos REST JSON y HTTP messaging
- Desarrollar REST APIs / Web Services con @RestController
- Construir una interface CRUD a BBDD con Spring REST

Ejemplo de proyecto consistente en:

- Construir una app CRM (Customer Relationship Manager) (será el client) que pida información
- Construir una Servicio CRM (será el server) que devuelva la información pedida

Cuestiones a tener en cuenta:

- ¿Cómo vamos a conectarnos al Servicio?
  - Llamadas REST API sobre HTTP
- ¿Qué lenguaje de programación vamos a usar?
  - REST es independiente del lenguaje
- ¿Qué formato de datos vamos a usar? ¿XML, JSON, CSV?
  - Las aplicaciones REST pueden usar cualquier formato, generalmente XML y JSON (más popular)

### 01-spring-boot-rest-crud

Crear una clase demo REST Controller en Java con Spring Boot (parte server):

`Añadir la dependencia Maven: spring-boot-starter-web`

```
  @RestController
  @RequestMapping("/test")
  public class DemoRestController {

    @GetMapping("/hello")
    public String sayHello() {
      return "Hello World!";
    }

  }
```

Usando Postman (o un navegador web) como client, este ejemplo podría probarse de la siguiente manera: `http://localhost:8080/test/hello`

**Java JSON Data Binding**

Data binding es el proceso de convertir JSON data a un Java POJO, poblando los datos del Java POJO con la información que tiene el JSON.
También es el proceso contrario, es decir, dados unos datos en un Java POJO, es el proceso de poblar un JSON.

También se le llama: Mapping, Serialization/Deseralization, Marshalling/Unmarshalling

Para realizar este Data binding, Spring Boot Starter Web incluye automáticamente una dependencia llamada Jackson Project. `https://github.com/FasterXML/jackson-databind`

Por defecto, Jackson llamará al método getter/setter apropiado para realizar la conversión.

De JSON a POJO llamará a métodos setter y de POJO a JSON llamará a métodos getter.

Por tanto para pasar de JSON a POJO, Jackson NO accede a campos privados de forma directa, es a través de los métodos setter.

Cuando se construyen aplicaciones REST con Spring, Spring gestiona automáticamente la integración con Jackson.

Cualquier data JSON pasada a un controlador REST es automáticamente convertida a un POJO. Y cualquier objeto Java devuelto desde un controlador REST será convertido automáticamente a JSON.

**Spring Boot REST POJO**

Vamos a crear un nuevo Service que devuelva como response una lista de estudiantes ante la request /api/students

Esta lista de estudiantes será convertida a un array JSON, con la ayuda de Jackson.

Para esto:

- Crearemos una clase Java POJO (campos, constructores, getters y setters) para Student
- Crearemos un Spring REST Service usando la anotación @RestController

Para probar este ejemplo, desde Postman hacer la siguiente request: `http://localhost:8080/api/students`

**Path Variables**

Vamos a recuperar un student por el id usando el siguiente endpoint `/api/students/{studentId}`, donde {studentId} es lo que se llama un path variable, y será sustituido por un valor concreto.

Para desarrollar esto:

- Vincularemos un path variable a un method parameter usando la anotación @PathVariable

```
  @GetMapping("/students/{studentId}")
  public Student getStudent(@PathVariable int studentId) {
    ...
  }
```

Para probar este ejemplo, desde Postman hacer la siguiente request: `http://localhost:8080/api/students/0`

**Exception Handling**

Si hacemos la siguiente petición en Postman: `http://localhost:8080/api/students/9999` veremos el siguiente error:

```
{
    "timestamp": "2023-11-12T06:01:56.068+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/api/students/9999"
}
```

Y en la terminal de ejecución de Spring veremos: `exception [Request processing failed: java.lang.IndexOutOfBoundsException: Index 9999 out of bounds for length 3] with root cause`

Lo que realmente tenemos que hacer es manejar la excepción y devolver un error en formato JSON que nos indique el motivo del error. Algo así:

```
{
  "status": 404,
  "message": "Student id not found - 9999",
  "timeStamp": 15261496
}
```

Para poder devolver esta respuesta, los pasos son los siguientes:

- Crear una clase de respuesta de errores personalizada
  - Esta respuesta es la que se envíará al cliente como JSON en el método de manejo de excepciones
  - Es una clase Java POJO con los campos de la respuesta, llamada, en el ejemplo, StudentErrorResponse
  - Jackson la convertirá a JSON
- Crear una clase de excepción personalizada
  - Esta excepción personalizada de student la usará nuestro REST Service
  - En el ejemplo la llamaremos StudentNotFoundException y extiende de RuntimeException
- Actualizar el REST Service para hacer un throw de la excepción si no se encuentra el student
  - Se hará un throw new StudentNotFoundException
- Añadir un método de manejo de excepciones en nuestro REST Service usando la anotación @ExceptionHandler. En el ejemplo el método se llama handleException
  - Este manejo de excepciones devolverá un ResponseEntity, que es un wrapper para un objeto HTTP de respuesta
  - ResponseEntity provee el control para especificar: HTTP status code, HTTP headers y Response body

**Global Exception Handling**

El código de un Exception handler es solo para un controlador REST específico y no puede reutilizarse por otros controladores.

Esto es un problema, porque en proyectos largos suele haber muchos controladores.

Idealmente, lo que necesitamos son exception handlers globales. Esto sirve para:

- Promociona la reutilización
- Centraliza el manejo de excepciones

Para poder hacer esto:

- Usaremos la anotación @ControllerAdvice, que es similar a un interceptor/filtro. Este es un ejemplo de AOP (Aspect Oriented Programming)
- Con esto, pre-procesamos peticiones en controladores
- Y post-procesamos respuestas para manejar excepciones
- Perfecto como manejador global de excepciones

La arquitectura es:

```
___________                           ______________         _____________
|  REST   |  /api/students/9999       | Controller |         |  REST     |
|  Client |  --------------------->   |   Advice   |  ---->  |  Service  |
|         |                           |            |         |           |
|         |  <---------------------   |  Exception |  <----  |      Throw|exception
|         |   {"status": 404, ...}    |  Handlers  |         |           |
|_________|                       ->  |____________|         |___________|
                                 |
                               Global
                               exception
                               handling
```

Proceso de desarrollo:

- Crear un nuevo @ControllerAdvice, llamado en el ejemplo StudentRestExceptionHandler
- Refactorizar nuestro REST service ... eliminar el código de manejo de excepciones
- Añadir el código de manejo de excepciones al @ControllerAdvice

Esto es una buena práctica.

### 02-spring-boot-rest-crud-employee

Vamos a crear un REST API con Spring Boot que se conecta a una base de datos.

En concreto, es una REST API para un Employee Directory.

El cliente REST podrá:

- Obtener una lista de employees
- Obtener un employee por su id
- Añadir un nuevo employee
- Actualizar un employee
- Eliminar un employee

Los métodos HTTP, endpoints y acción CRUD son:

```
POST          /api/employees                    Crear un nuevo employee
GET           /api/employees                    Leer una lista de employees
GET           /api/employees/{employeeId}       Leer un employee
PUT           /api/employees                    Actualizar un employee existente
DELETE        /api/employees/{employeeId}       Eliminar un employee existente
```

El proceso de desarrollo será el siguiente:

- Configurar el entorno de BBDD de desarrollo
- Crear el proyecto Spring Boot usando VSCode
- Crear un nuevo employee
- Leer una lista de employees
- Leer un employee
- Actualizar un employee existente
- Eliminar un employee existente

La arquitectura será la siguiente:

```
  Employee               Employee             Employee
    REST       <------>  Service   <------->    DAO      <------>  BBDD
  Controller
```

**Configuración de BBDD**

Para el proyecto se usa MariaDB y uso esta imagen Docker:

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

Ejecutar las consultas del archivo `employee-directory.sql` para crear una nueva tabla de BBDD llamada employee y poblarla de datos de prueba.

El archivo se encuentra en el directorio `spring-boot-employee-sql-script`

**REST DAO**

- Usamos la anotación @Repository para nuestra implementación de la interface EmployeeDAO. Nuestra implementación se llama EmployeeDAOJpaImpl
- Añadimos el campo EntityManager
- Creamos un constructor al que le inyectamos el EntityManager (automáticamente gracias a Spring Boot)

```
  @Repository
  public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
      entityManager = theEntityManager;
    }

    ...
  }
```

- Desarrollaremos los métodos CRUD usando JPQL

Proceso de desarrollo:

- Actualizar la configuración de BD en nuestro fichero application.properties
- Crear una entidad Employee (anotación @Entity)
- Crear una interface DAO
- Crear la implementación DAO (anotación @Repository)
- Crear el controlador REST para usar nuestro DAO (anotación @RestController)

- Testing: `http://localhost:8080/api/employees`

**Capa Service**

Vamos a definir servicios con la anotación @Service

Esta capa se encuentra entre nuestro EmployeeRestController y nuestro EmployeeDAO.

El propósito detrás de la creación de una Capa de Servicio es:

- Aplicar el patrón de diseño Service Facade
- Tener una capa intermedia para la lógica de negocio
- Integrar data desde múltiples fuentes (DAO/repositorios) Con esto proveemos al controlador con una única vista de la data que integramos desde distintos fuentes de datos de backend.

Aplicar una capa de servicio que llame a una capa DAO es una buena práctica.

La anotación @Service es una anotación especializada para servicios, "hija" de la anotación @Component

Spring registrará automáticamente la implementación Service gracias al escaneo de componentes habilitado tras anotar con @Service

Proceso de desarrollo:

- Definir la interface Service (EmployeeService)
- Definir la implementación Service (EmployeeServiceImpl y anotarla con @Service)
  - Inyectar el DAO (EmployeeDAO)

Capa de servicio: Mejor práctica:

- Aplicar transaccionalidad en la capa de servicio
- Es responsabilidad de la capa de servicio gestionar la transaccionalidad
- Para implementar este código:
  - Aplicar anotación @Transactional en los métodos del servicio
  - Eliminar anotación @Transactional en los métodos del DAO si ya existían

**DAO: Add, Update, Delete**

Vamos a implementar los siguientes métodos DAO:

- Obtener un employee por ID
- Añadir un nuevo employee
- Actualizar un employee existente
- Eliminar un employee existente

**Service: Add, Update, Delete**

Vamos a implementar los siguientes métodos en la capa Service:

- Obtener un employee por ID
- Añadir un nuevo employee
- Actualizar un employee existente
- Eliminar un employee existente

**Métodos REST en Controlador: Add, Update, Delete**

Vamos a implementar los siguientes métodos en el controlador:

- Obtener un employee por ID
- Añadir un nuevo employee
- Actualizar un employee existente
- Eliminar un employee existente

- Testing: Importar en Postman el archivo Darby-04-spring-boot-rest-crud-employee.postman_collection.json

### 03-spring-boot-rest-crud-employee-with-spring-data-jpa

Hasta ahora en el curso se ha utilizado el API JPA standard en nuestra capa DAO.

Vamos a cambiar para usar Spring Data JPA en nuestra capa DAO.

El problema:

- Vimos como crear un DAO para Employee
- ¿Qué hacemos si necesitamos crear un DAO para otra entity?
  - Customer, Student, Product, Book...
- ¿Necesitamos repetir el mismo código de nuevo? Es bastante

Hemos visto que hay un patrón a la hora de crear un DAO. El código de los DAOs es básicamente el mismo, cambiando solo el tipo de Entity y la primary key.

Lo que me gustaría:

- Me gustaría decirle a Spring que creara por mi un DAO
- Que enchufe el tipo de Entity y la primary key
- Que me de, ya hecho, las funciones básicas de un CRUD

Solución:

- Spring Data JPA. Creamos un DAO y enchufamos el tipo de entity y la primary key, y Spring nos da una implementación CRUD

Con esta solución reducimos hasta en un 70% de código DAO, dependiendo del caso.

Spring Data JPA proporciona una interface, JpaRepository, que expone ciertos métodos (findAll(), findById(), save(), deleteById()...)

Proceso de desarrollo:

- Extender nuestra interface DAO de la interface JpaRepository
- Usar el repository en nuestra app
- No es necesario una clase de implementación de nuestra interface DAO

```
  public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Ya está... No es necesario escribir ningún código

  }
```

Indicamos el tipo de Entity (Employee) y el tipo de nuestra primary key (Integer)

Para usar nuestro repository en la app:

```
  @Service
  public class EmployeeServiceImpl implements EmployeeServ {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
      employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll() {

      // Podemos usar los métodos que nos regala JpaRepository
      return employeeRepository.findAll();
    }

  }
```

Recordar:

- Usamos Spring Data JPA porque nos ayuda a reducir la cantidad de código para crear un DAO
  - Sin Spring Data JPA: tenemos que crear una interface DAO con los métodos que necesitamos y luego una clase DAO de implementación de esos métodos (2 ficheros y más de 30 líneas de código)
  - Con Spring Data JPA: en nuestra interface extendemos JpaRepository y ya (1 fichero y 3 líneas de código)

Características avanzadas:

- Se puede extender añadiendo queries personalizadas con JPQL
- Query Domain Specific Language (Query DSL)
- Definición de métodos personalizados (código de bajo nivel) para cosas concretas que necesitemos

Por si acaso, indicar que el patrón DAO y el patrón Repository no son lo mismo.

Notas importantes:

```
You can't compare DAO and Spring Data JPA.

DAO (Data Access Object): The DAO pattern is used for encapsulating the interaction with a data source, such as a database. The primary purpose of the DAO is to abstract away the details of data storage and retrieval, providing a clean interface for the rest of the application to work with data. DAOs contain methods for performing CRUD (Create, Read, Update, Delete) operations on data entities. In modern Spring applications, the DAO pattern is often implemented using technologies like Spring Data JPA, which provides a higher-level and more convenient way to work with databases.


Spring Data JPA is a subproject of the larger Spring Data framework that simplifies and enhances the interaction with databases in Java applications, specifically in the context of Java Persistence API (JPA)-compliant data access. It aims to provide a more convenient and productive way to work with databases while reducing the amount of boilerplate code that developers need to write. Spring Data JPA integrates the features of Spring Framework and JPA, offering a higher-level abstraction for data access operations.

Here are some key aspects and features of Spring Data JPA:

JPA Integration: Spring Data JPA builds on top of the Java Persistence API (JPA), which is a standard API for object-relational mapping (ORM). JPA defines the standard for mapping Java objects to relational database tables and performing CRUD operations using entity classes and annotations.

Repository Abstraction: The central feature of Spring Data JPA is the repository abstraction. It provides a set of interfaces that you can extend in your application to create repositories for your data entities. These repositories offer common database operations such as CRUD (Create, Read, Update, Delete) operations, pagination, sorting, and custom query methods.

Query Methods: Spring Data JPA allows you to create query methods by simply defining method names according to a specific naming convention. For example, you can define a method like findByLastName(String lastName) in your repository interface, and Spring Data JPA will automatically generate the appropriate SQL query for you based on the method name.

Custom Queries: In addition to query methods, Spring Data JPA supports the creation of custom queries using the @Query annotation or method naming conventions. This allows you to write more complex queries when needed.

Pagination and Sorting: Spring Data JPA provides built-in support for paginating and sorting query results. You can specify the page size, the current page number, and the sorting criteria directly in your repository methods.

Auditing: Spring Data JPA offers auditing capabilities that automatically populate audit fields like creation date, modification date, and user information without explicit coding.

Integration with Spring Boot: Spring Data JPA seamlessly integrates with Spring Boot applications, making it easy to set up and configure. Spring Boot provides auto-configuration for data source connections and transaction management.

Native Queries: While Spring Data JPA encourages the use of JPQL (Java Persistence Query Language), it also supports native SQL queries when necessary.

Overall, Spring Data JPA streamlines database interactions and reduces the need for writing low-level database-related code. It simplifies the development process, improves code quality, and enhances productivity by providing a consistent and powerful way to work with data in Spring applications.
```

```
DAO is still used today because if you want to have your code tested, and more control over the library it is much better to Hibernate and define your own methods and what they are going to return. Yes, you can use Spring DATA for similar things, but if you reach a point where you might need to use EntityManager for something or try to handle your own transactions which you cannot do with Spring Data because it uses too much magic. Anything that uses too much magic under the hood is not good, which will lock you down with that specific library and in case something happens will make debugging impossible.

The reason why DAO and Spring Data cannot be compared is simply because Spring Data is a repository pattern it is not a DAO pattern.
```

- Testing: Importar en Postman el archivo Darby-04-spring-boot-rest-crud-employee.postman_collection.json

### 04-spring-boot-rest-crud-employee-with-spring-data-rest

¿Podemos aplicar lo que hemos visto de Spring Data JPA a los REST APIs?

El problema:

- Hemos visto como crear un API REST para Employee (con su anotación @RestController, etc.)
- ¿Que pasa si necesitamos crear otro API REST para otra entity?
  - Customer, Student, Product, Book...
- ¿Necesitamos repetir el mismo código de nuevo? Es bastante

Lo que me gustaría:

- Me gustaría decirle a Spring que creara por mi un REST API
- Que use my JpaRepository (entity, primary key) existente
- Que me de, ya hecho, las funciones básicas de un REST API CRUD

Solución:

- Spring Data REST. Es un proyecto Spring separado que puede usarse con Spring o Spring Boot y que mejora el JpaRepository existente y nos da una implementación REST CRUD.

Con esta solución reducimos bastante el código REST y no se necesita nuevo código para configurarlo.

Spring Data REST expondrá estos endpoints automáticamente:

```
  POST    /employees
  GET     /employees
  GET     /employees/{employeeId}
  PUT     /employees/{employeeId}
  DELETE  /employees/{employeeId}
```

¿Cómo funciona?

- Spring Data REST escaneará el proyecto en busca de JpaRepository
- Expondrá REST APIs para cada entity type de nuestro JpaRepository de la siguiente forma
- Simple pluralized form

  - Primer carácter de la Entity type en minúsculas
  - Añade una 's' a la entity
  - Ejemplo:

  ```
    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    }
  ```

  En este caso el endpoint es /employees

Proceso de desarrollo

- Añadir Spring Data REST al fichero Maven POM... y ya esta!!

Resumen de qué necesitamos:

- La entity: Employee
- JpaRepository: EmployeeRepository extends JpaRepository
- Dependencia Maven POM para: spring-boot-starter-data-rest

La arquitectura pasa de:

```
  Employee               Employee                Employee
    REST       <------>  Service   <------->    Repository     <------>  BBDD
  Controller                                  Spring Data JPA
```

A:

```
  Spring Data               Employee
    REST       <------>    Repository      <------>  BBDD
  /employees             Spring Data JPA

```

Los endpoints Spring Data REST son cumplimentan HATEOAS (Hypermedia as the Engine of Application State)

Hypermedia-driven sites proveen información para acceder a interfaces REST.

Es mejor pensar en ello como metadata para la data REST devuelta por estos REST APIs (la response).

Ejemplo de response para: GET /employees/3

```
{
  "firstName": "Avani",
  "lastName": "Gupta",
  "email": "avani@luv2code.com",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/3"
    },
    "employee": {
      "href": "http://localhost/8080/employees/3"
    }
  }
}
```

Donde firstName, lastName y email es la data de Employee y \_links es la response metadata que viene con los links a la data, que describe la data.

Para una colección, la metadata incluye el tamaño de página, elementos totales, páginas...

Ejemplo de response para: GET /employees

```
{
  "_embedded": {
    "employees": [
      {
        "firstName": "Avani",
        ...
      },
      ...
    ]
  },
  "page": {
    "size": 20,
    "totalElements": 5,
    "totalPages": 1,
    "number": 0
  }
}
```

HATEOAS también usa el formato de datos Hypertext Application Language (HAL), que es también el formato de data JSON.

Características avanzadas:

- Paginación, ordenación y búsqueda
- Se puede extender añadiendo queries personalizadas con JPQL
- Query Domain Specific Language (Query DSL)
- Definición de métodos personalizados (código de bajo nivel) para cosas concretas que necesitemos

Notas importantes:

```
> > What is the practical use case of Spring Data REST?

1. Rapid API Development: Spring Data REST allows you to quickly create a fully functional RESTful API for your data models without writing a lot of boilerplate code. This can significantly speed up the development process.

2. Data Exposition: If you want to make your data accessible to external consumers or other parts of your application via a RESTful interface, Spring Data REST provides a convenient way to do so. This is particularly useful when building microservices or exposing data for consumption by mobile apps or web clients.

3. Simplified CRUD Operations: Spring Data REST provides default endpoints and operations for creating, reading, updating, and deleting (CRUD) resources, which can save you from writing repetitive code for these common operations.

In summary, Spring Data REST is a valuable tool for quickly creating RESTful APIs, especially when you have data models managed by Spring Data repositories. It simplifies API development and adheres to RESTful principles, making it a practical choice for building web services and APIs.


> > When to use Spring Data JPA Vs Spring Data REST?

Spring Data JPA and Spring Data REST are two different components within the Spring Data framework, and they serve distinct purposes. You would typically use them in different scenarios based on your project requirements:

Spring Data JPA:

Use Spring Data JPA when you want to simplify the data access layer of your application, particularly when working with relational databases. Spring Data JPA provides a set of abstractions and helper methods to interact with your database, making it easier to work with JPA (Java Persistence API) entities.

It is suitable for applications where you need to define and manage your data model using Java entities and need to perform CRUD operations on these entities.

Spring Data JPA is the right choice when you want to take advantage of JPA's object-relational mapping features, such as entity relationships, querying, and transaction management.

Spring Data JPA is typically used for building the persistence layer of your application, allowing you to store and retrieve data from a database.


Spring Data REST:

Use Spring Data REST when you want to quickly expose your data models managed by Spring Data repositories as a RESTful API. Spring Data REST generates a RESTful API for your data models without the need to write extensive API endpoints and controllers.

It is suitable for applications where you need to make your data accessible over HTTP, either for external clients (e.g., mobile apps, web applications) or for integrating multiple services in a microservices architecture.

Spring Data REST is a higher-level abstraction that builds on top of Spring Data JPA (or other Spring Data modules) to create a RESTful API. You typically use Spring Data JPA or another Spring Data module to define your data model and use Spring Data REST to expose that model as a REST API.

Spring Data REST is focused on building the presentation layer of your application, allowing clients to interact with your data via HTTP, following REST principles.

In many cases, you may use both Spring Data JPA and Spring Data REST together within the same application. Spring Data JPA is used to define and manage your data model, while Spring Data REST is used to expose that data model as a RESTful API. This combination can provide a powerful and efficient way to build full-stack applications where you need both data persistence and a web API for data access.

To summarize, Spring Data JPA is used for the data access and persistence layer, while Spring Data REST is used for quickly exposing your data models as RESTful APIs. The choice between them depends on your project's architectural and functional requirements.
```

- Testing: Importar en Postman el archivo Darby-04-spring-boot-rest-crud-employee-with-spring-data-rest.postman_collection.json

Para personalizar el endpoint se puede usar la siguiente property: `spring.data.rest.base-path=/magic-api`

**Spring Data REST configuraciones, paginación y ordenación**

Antes hemos visto que:

- Simple pluralized form

  - Primer carácter de la Entity type en minúsculas
  - Añade una 's' a la entity
  - Ejemplo:

  ```
    public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    }
  ```

  En este caso el endpoint es /employees

¿Pero que pasa con los plurales complejos? Por ejemplo: Person --> People

Spring Data REST no maneja las formas plurales complejas. En el caso del ejemplo, necesitamos especificar el plural.

Además, ¿Qué ocurre si queremos exponer un nombre de recurso diferente? Por ejemplo: en vez de /employees --> queremos usar /members

La solución es indicar el nombre plural/path con una anotación

```
  @RepositoryRestResource(path="members")
  public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  }
```

Con respecto a la paginación, Spring Data REST devuelve por defecto 20 elementos, es decir: `Page size = 20`

Podemos navegar a diferentes páginas de data usando query param

```
  http://localhost:8080/employees?page=0

  http://localhost:8080/employees?page=1
```

Donde page empieza en 0.

Configuración usando el fichero properties:

- spring.data.rest.base-path
- spring.data.rest.default-page-size
- spring.data.rest.max-page-size
- ...

Con respecto a la ordenación, podemos ordenar por los nombres de propiedades de nuestra entidad (ascendente por defecto)

```
  http://localhost:8080/employees?sort=lastName

  http://localhost:8080/employees?sort=firstName,desc

  http://localhost:8080/employees?sort=lastName,firstName,asc
```
