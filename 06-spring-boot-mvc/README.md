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
