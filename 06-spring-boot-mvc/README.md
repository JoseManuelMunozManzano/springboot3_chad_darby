# 06-spring-boot-mvc

MVC con Thymeleaf.

`https://www.thymeleaf.org/`

Vamos a ver estas secciones de MVC sin prestar tanta atención a Thymeleaf porque mi objetivo es usar React o Angular.

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
