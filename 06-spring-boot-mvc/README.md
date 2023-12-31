# 06-spring-boot-mvc

MVC con Thymeleaf.

`https://www.thymeleaf.org/`

Vamos a ver estas secciones de MVC sin prestar tanta atención a Thymeleaf porque mi objetivo es usar React o Angular.

Tutorial Patrón DTO, que no se ve en el curso: `https://www.youtube.com/watch?v=5yquJa2x3Ko`

Todo lo que pueda aprender de Thymeleaf bienvenido sea, pero no quiero profundizar mucho.

Pasos:

- Añadir Thymeleaf al fichero Maven POM:

![alt text](./images/AddThymeleaf.png)

Y ya está, no hace falta añadir más configuración para poder usar Thymeleaf con Spring Boot.

- Desarrollar un Controller Spring MVC

Como se ha hecho hasta ahora en REST, usando la anotación @Controller.

![alt text](./images/SpringMVCController.png)

Se indica en la imagen que se buscará automáticamente una plantilla helloworld.html

En Spring Boot, las plantillas de Thymeleaf hay que dejarlas, por defecto, en la siguiente carpeta: `src/main/resources/templates` y los ficheros tendrán extensión `.html`

- Crear una plantilla Thymeleaf, es decir, un fichero html

![alt text](./images/ThymeleafTemplate.png)

Características adicionales:

- Bucles y condicionales
- Integración de CSS y JavaScript
- Diseños de plantillas y fragmentos

## 01-thymeleaf-helloworld

Un sencillo ejemplo con Thymeleaf. Hay un controller y un template hecho con Thymeleaf.

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/hello`

Podemos añadir CSS de las siguientes maneras:

- Fichero CSS local como parte del proyecto
- Referenciar ficheros CSS remotos

Proceso de desarrollo:

- Crear fichero CSS
  - Spring Boot buscará recursos estáticos en el directorio: `src/main/resources/static`
  - Nosotros, dentro de static, crearemos el directorio css y dentro el fichero demo.css
- Referenciar CSS en plantilla Thymelef
  - ![alt text](./images/ReferenceCSSFile.png)
- Aplicar estilo CSS
  - ![alt text](./images/AplicarCSS.png)

Spring Boot buscará (de arriba a abajo) recursos estáticos en los siguientes directorios:

- /src/main/resources
  - /META-INF/resources
  - /resources/
  - /static
  - /public

Lo más normal en proyectos reales es situar los recursos estáticos en las carpetas /static o /public

Uso de Librerías CSS como Bootstrap

- Instalación local
  - Descargar el fichero Bootstrap y añadirlo al directorio /static/css
  - Lo referenciamos
    - ![alt text](./images/ReferenciarFicheroBootstrap.png)
- Uso de fichero Bootstrap CDN
  - ![alt text](./images/BootstrapRemoto.png)

Componentes de una aplicación Spring MVC

- WEB PAGES: Páginas Web para diseñar componentes UI
- BEANS: Una colección de Spring Beans (controllers, services, etc...)
- SPRING CONFIGURATION: Configuración Spring (XML, Anotaciones o Java)

![alt text](./images/ComoFuncionaMVC.png)

Leer Datos de Formulario con Spring MVC

![alt text](./images/FormDataApplicationFlow.png)

La clave es entender que vamos a usar un controlador (HelloWorldController) para manejar dos peticiones.

![alt text](./images/FormDataController.png)

Proceso de desarrollo:

- Crear clase Controller
- Mostrar formulario HTML
  - Crear método en el controlador para mostrar formulario HTML
  - Crear View para el formulario HTML
- Procesar formulario HTML
  - Crear método en el controlador para procesar formulario HTML
  - Desarrollar View de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showForm`

Añadir data al Model Spring MVC

- El Model es un contenedor para los datos de la aplicación
- En el controlador:
  - Se puede poner cualquier cosa en el modelo
  - cadenas, objetos, info de la BD, etc...
- La página View puede acceder a la data desde el model.

Como ejemplo:

- Queremos crear un nuevo método para procesar data de formulario
- Leemos la data del formulario: student's name
  - HttpServletRequest request
  - request.getParameter("studentName");
- Convertimos el nombre a mayúsculas
- Añadimos la versión en mayúsculas al model
  - model.addAttribute("message", result);
- En la plantilla View obtenemos el dato que añadimos antes al model. Notar que el atributo (message) tiene que ser el mismo
  - <span th:text="${message}" />

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showForm`

Leer data de formulario HTML con la anotación @RequestParam

- En vez de usar HttpServletRequest y luego request.getParameter utilizamos otra técnica propia de Spring
  - @RequestParam("studentName") String theName

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showForm`

GetMapping y PostMapping

- Con un método HTTP GET, la data del formulario se añade al final de la URL en la forma nombre/pares de valores
  - theUrl?field1=value1&field2=value2...
  - @RequestMappint("/endpoint") sirve para manejar el envío de datos de formulario, para todos los tipos de métodos HTTP
  - @GetMapping("/processForm") solo maneja métodos HTTP GET
- Cuándo usar GET
  - Bueno para hace debug
  - Marcador o email URL
  - Limitaciones en la longitud de la data (1000 caractéres)
- Enviar data con un método HTTP POST
  - La data se envía en el body del mensaje de la petición HTTP
  - Ejemplo solo para POST: @RequestMapping(path="/processForm", method=RequestMethod.POST)
  - @PostMapping("/processForm") solo maneja métodos HTTP POST
- Cuándo usar POST
  - No se puede marcar o email URL
  - No tiene limitaciones en la longitud de la data
  - Puede enviarse código binario

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showForm`

Spring MVC Form Data Binding - Campos de Texto

![alt text](./images/FormTextFieldsController.png)

- Los formularios de Spring MVC pueden hacer uso de data binding
- Esto es, automáticamente establecen / recuperan data de un objeto / bean de Java
- En nuestro controller de Spring
  - Antes de mostrar el formulario, debes añadir un model attribute
  - Esto es un bean que retiene datos del formulario para el data binding
  - ![alt text](./images/ModelAttribute.png)
- ![alt text](./images/FormIsLoaded.png)
- ![alt text](./images/FormIsSubmitted.png)

Proceso de desarrollo:

- Crear clase Student
- Cerar clase controlador StudentController
- Crear formulario HTML
- Crear código de procesamiento de formulario
- Crear página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Spring MVC Form Data Binding - Listas Drop down

![alt text](./images/DropDownList.png)

Proceso de desarrollo:

- Actualizar formulario HTML
- Actualizar clase Student - añadir getter/setter para la nueva property
- Actualizar página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Si queremos que los valores no aparezcan hardcodeados en la página HTML, podemos ponerlos en el fichero properties y leerlos de ahí.

Proceso de desarrollo:

- Añadir lista de paises al fichero application.properties
- Inyectar los paises en StudentController usando la anotación @Value
- Añadir la lista de paises al model
- En el formulario HTML, generar lista de tags <option> para los paises

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Spring MVC Form Data Binding - Radio buttons

![alt text](./images/RadioButtons.png)

Proceso de desarrollo:

- Actualizar formulario HTML
- Actualizar clase Student - añadir getter/setter para la nueva property
- Actualizar página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Si queremos que los valores no aparezcan hardcodeados en la página HTML, podemos ponerlos en el fichero properties y leerlos de ahí.

- Añadir lista de lenguajes de programación al fichero application.properties
- Inyectar los lenguajes de programación en StudentController usando la anotación @Value
- Añadir la lista de lenguajes de programación al model
- En el formulario HTML, generar lista de tags <input> para los lenguajes de programación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Spring MVC Form Data Binding - Check Box

![alt text](./images/CheckBox.png)

Proceso de desarrollo:

- Actualizar formulario HTML
- Actualizar clase Student - añadir getter/setter para la nueva property
- Actualizar página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

Si queremos que los valores no aparezcan hardcodeados en la página HTML, podemos ponerlos en el fichero properties y leerlos de ahí.

- Añadir lista de Sistemas Operativos al fichero application.properties
- Inyectar los Sistemas Operativos en StudentController usando la anotación @Value
- Añadir la lista de Sistemas Operativos al model
- En el formulario HTML, generar lista de tags <input> para los Sistemas Operativos

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showStudentForm`

```
In Spring MVC, when a request is made to a controller method, the framework is responsible for creating instances of certain objects and passing them as arguments to the method. This process is known as argument resolution.

Here are some commonly used objects that can be passed as method parameters in a Spring MVC controller:

1. Model: The Model interface is provided by Spring to pass data between the controller and the view. When you include a Model parameter in your controller method, Spring automatically provides an instance of it.

@Controller
public class MyController {

    @RequestMapping("/example")
    public String example(Model model) {
        // Use the model to add attributes
        model.addAttribute("message", "Hello, World!");
        return "example-view";
    }
}


2. HttpServletRequest and HttpServletResponse: You can include HttpServletRequest and HttpServletResponse as parameters in your method to gain access to the raw HTTP request and response.

@RequestMapping("/example")
public String example(HttpServletRequest request, HttpServletResponse response) {
    // Access request and response objects
    // ...
    return "example-view";
}


3. @RequestParam: Use @RequestParam to extract values from query parameters or form data.

@RequestMapping("/example")
public String example(@RequestParam String name) {
    // Use the value of the 'name' parameter
    // ...
    return "example-view";
}


4. @PathVariable: Extract values from URI templates.

@RequestMapping("/example/{id}")
public String example(@PathVariable Long id) {
    // Use the value of the 'id' path variable
    // ...
    return "example-view";
}


5. @RequestBody: Extract the entire request body.

@PostMapping("/example")
public String example(@RequestBody String requestBody) {
    // Process the entire request body
    // ...
    return "example-view";
}


The instances of these objects are typically created and managed by the Spring MVC framework through various resolvers. Spring uses reflection and other mechanisms to populate these parameters with the appropriate values before invoking the controller method.

The actual creation and population depend on the specific resolver used, and Spring has default resolvers for common types. You can also customize this process by creating custom argument resolvers if needed.
```

## 02-validationdemo

Spring MVC Form Validation

Necesitamos validar la información que el usuario ha informado en el formulario:

- Campos requeridos
- Números válidos en un rango
- Formato válido (código postal por ejemplo)
- Nuestras propias reglas de negocio

- Java incluye una API estándar de Validaciones de Beans
  - https://beanvalidation.org/
- Define un modelo de metadata y una API para validación de entidades
- Spring Boot y Thimeleaf también soportan este API de Validaciones de Beans

Características disponibles en la Validación de Beans

- Requerido
- Validar longitud
- Validar números
- Validar con expresiones regulares
- Validaciones personalizadas

Anotaciones usadas en las validaciones

- @NotNull - Chequea que el valor no sea null
- @Min - El número indicado debe ser >= valor
- @Max - El número indicado debe ser <= valor
- @Size - El tamaño debe ser el indicado
- @Pattern - Para expresiones regulares
- @Future/@Past - Una fecha debe estar en el futuro o pasado de otra dada
- ...

Nuestra hoja de ruta

- Configurar nuestro entorno de desarrollo
- Escribir código para comprobar un campo requerido
- Validar un rango numérico: min, max
- Validar usando expresiones regulares (regexp)
- Validación personalizada

Campos Requeridos

Proceso de desarrollo:

- Crear clase Customer y añadir reglas de validación
  - Haremos el campo lastName requerido
- Añadir código del Controller para mostrar el formulario HTML
  - Usaremos Model para compartir información entre el Controller y las Views
- Desarrollar formulario HTML y añadir soporte de validación
- Realizar validación en la clase Controller
  - Usaremos anotaciones @Valid y @ModelAttribute para indicar a Spring MVC que realice validaciones
  - El resultado de las validaciones irá a un BindingResult
- Crear página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Anotación @InitBinder

El ejemplo anterior tiene el problema de que, si indicamos lastName con espacios en blanco, pasa la validación y no deberia.

Para evitar esto tenemos que hacer un trim de los campos de entrada.

- La anotación @InitBinder funciona como un pre-procesador
- Pre-procesará primero cada petición web que vaya a nuestro controller
- Por tanto, un método anotado con @InitBinder se ejecutará lo primero de todo, pre-procesará la data de la petición y seguirá por el controller

Lo vamos a usar en este ejemplo para:

- Eliminar espacios en blanco por delante y por detrás
- Si un String solo tiene espacios en blanco lo convertiremos en null

Con esto, sin indicamos lastName solo con espacios en blanco, se convertirá en null y la validación no pasará.

Más información sobre @InitBinder: `https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-custom-property-editor.html`

Más información sobre editores de propiedades personalizadas: `https://www.baeldung.com/spring-mvc-custom-property-editor`

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Rango de números: @Min y @Max

- Añadir un nuevo campo de entrada en el formulario: Free Passes
- El usuario podrá introducir un valor entre 0 y 10

Proceso de desarrollo:

- Añadir una regla de validación a Customer
- Mostrar un mensaje de error en el formulario HTML
- Realizar validación en la clase Controller
- Actualizar la página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Aplicar Expresiones Regulares

`https://docs.oracle.com/javase/tutorial/essential/regex/`

- Vamos a usar expresiones regulares para validar un código postal
- El usuario puede introducir solo 5 caracteres / dígitos

Proceso de desarrollo:

- Añadir una regla de validación a Customer
- Mostrar un mensaje de error en el formulario HTML
- Actualizar la página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Campos Integer requeridos

Para que esto funcione, se cambia, en la clase Customer, el campo fressPasses, de ser un primitivo int a la clase Integer.

Esto es para evitar el fallo de no poder convertir una propiedad de tipo String a un int primitivo.

No olvidar cambiar también los métodos getter / setter

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Manejar Strings por campos Integer y mensajes personalizados

Si en el campo de formulario Free Passes introducimos texto y pulsamos el botón submit, como ese campo esperaba un entero, falla indicando que no puede convertir un String a un Integer. Es un error muy feo que debemos personalizar.

Para resolver este problema:

- Vamos a crear un mensaje de error personalizado en un nuevo fichero de mensajes
  - El fichero tiene que ubicarse y llamarse así: `src/resources/messages.properties`
  - typeMismatch.customer.freePasses=Invalid number
  - Indicamos tipo de error (typeMismatch), el nombre del model (customer), el nombre del campo (freePasses) y el mensaje personalizado (Invalid number)

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`

Trucos de Debug para Errores de Nombres Personalizados en fichero messages.properties

- Añadimos a CustomerController logs para inspeccionar el objeto bindingResult en la consola
- Este objeto tiene mucha información que podemos usar para descifrar lo que ocurre durante el proceso de validación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/` e indicar Last name y un texto en Free passes, y pulsar el botón Submit.

Con esto obtenemos el tipo de error typeMismatch.customer.freePasses que es el que podemos incluir en el fichero messages.properties. Ver la parte codes [...]

Validación Personalizada

- Nos permite realizar validaciones personalizadas basadas en nuestras reglas de negocio
- Spring MVC llama a nuestra validación personalizada
- La validación personalizada devuelve un valor booleano para paso/fallo (true/false)

Vamos a crear una anotación Java personalizada desde cero llamada @CourseCode que se aplicará a un campo dado.

![alt text](./images/CustomJavaAnnotation.png)

Proceso de desarrollo:

- Crear una regla de validación personalizada
  - Crear anotación @CourseCode
    - ![alt text](./images/CourseCodeAnnotation.png)
  - Crear CourseCodeConstraintValidator, que es una clase Helper donde pondremos nuestras reglas de negocio
    - ![alt text](./images/CourseCodeConstraintValidator.png)
- Añadir regla de validación a la clase Customer
- Mostrar mensaje de error en el formulario HTML
- Actualizar la página de confirmación

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/`
