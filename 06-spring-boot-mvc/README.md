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
  - request.getParametr("studentName");
- Convertimos el nombre a mayúsculas
- Añadimos la versión en mayúsculas al model
  - model.addAttribute("message", result);
- En la plantilla View obtenemos el dato que añadimos antes al model. Notar que el atributo (message) tiene que ser el mismo
  - <span th:text="${message}" />

Para testear ejecutar el proyecto e ir a la ruta: `http://localhost:8080/showForm`

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
