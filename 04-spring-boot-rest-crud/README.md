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
