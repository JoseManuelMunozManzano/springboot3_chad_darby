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
