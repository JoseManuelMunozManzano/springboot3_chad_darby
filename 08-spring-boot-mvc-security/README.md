# 08-spring-boot-mvc-security

Cosas que vamos a aprender:

- Securizar Apps Web hechas con Spring MVC
- Desarrollar páginas de login (por default y personalizadas)
- Definir usuarios y roles con simple authentication
- Proteger URLs basado en roles
- Ocultar/mostrar contenido basado el roles
- Almacenar usuarios, passwords y roles en BD (primero en texto plano y luego encriptado)

Spring Security Model

- Spring Security define un framework para seguridad
- Implementado usando Servlet Filters en segundo plano
- Hay dos métodos de securizar una app: declarativa y programática

Spring Security con Servlet Filters

- Servlet Filters se usan para preprocesar / postprocesar peticiones web
- Servlet Filters pueden enrutar peticiones web basadao en lógica de seguridad
- Spring provee una gran cantidad de funciones de seguridad con servlet filters

Visión Spring Security

![alt text](./images/SpringSecurityOverview.png)

Spring Security en Acción

![alt text](./images/SpringSecurityInAction.png)

Conceptos de Seguridad

- Autenticación: comprobar id de usuario y password contra las credenciales almacenadas en app / db
- Autorización: comprobar si un usuario tiene un rol autorizado

Seguridad Declarativa

- Definir restricciones de seguridad de la app en la configuración
  - @Configuration
- Provee separación de cometidos entre el código de la app y seguridad

Seguridad Programática

- Spring Security provee una API para personalizar la codificación de la seguridad
- Provee una gran personalización para requerimientos específicos de la app

Habilitar Spring Security

- Editar pom.xml y añadir spring-boot-starter-security
- Con esto, automáticamente se securizan todos los endpoints de la app

Endpoints Securizados

- Cuando accedamos a nuestra app, Spring Security pedirá un login
- El usuario por defecto será user y el password se genera automáticamente al arrancar la app (en la consola)
- Esto es para testing. Todo esto se puede personalizar en el fichero application.properties
  - spring.security.user.name=jmmunoz
  - spring.security.user.password=my_password

Diferentes métodos para hacer Login

- HTTP Basic Authentication
  - Feo. No se usa en el curso
  - ![alt text](./images/BasicAuthentication.png)
- Formulario login por defecto
  - Spring Security provee un formulario login por defecto
  - ![alt text](./images/DefaultLoginForm.png)
- Formulario login personalizado
  - Nuestro propio look-and-feel, HTML + CSS
  - ![alt text](./images/CustomLoginForm.png)

## 01-spring-boot-spring-mvc-security-default

Se va a realizar un proyecto MVC Security, usando además MVC Web App y Thymeleaf.

`https://docs.spring.io/spring-security/reference/`

![alt text](./images/OurExample.png)

Proceso de desarrollo del proyecto:

- Crear un proyecto Spring usando VSCode
  - Añadir dependencias para Spring MVC Web App, Security, Thymeleaf
  - ![alt text](./images/Dependencies.png)
- Desarrollar nuestro controller Spring
  - ![alt text](./images/DemoController.png)
- Desarrollar nuestra view con Thymeleaf
  - ![alt text](./images/ThymeleafView.png)

Para testear ir a la siguiente URL: `http://localhost:8080`

Veremos el formulario login por defecto suministrado por Spring Security.

- Usuario: user
- Password: aparece en la consola donde se ejecuta Spring

NOTA: En las herramientas de desarrollador ir a la pestaña Application y luego, en la parte izquierda seleccionar Cookies y eliminar la entrada con el usuario y password, asociada a la ejecución de nuestra app, si queremos que nos vuelva a salir la pantalla de login. Si no, tras hacer login la primera vez, el usuario aparecerá como loggeado siempre, ya que está basado en una sesión de navegador.

![alt text](./images/DeleteEntryCookie.png)

Esto es un problema que solo se da durante desarrollo y testing. No se da en producción.

```
Spring Boot stores the session data in a serialized file between restarts. The session cookies have a default timeout of 30 minutes. So as long as you restart within that time period, you can reattach to the older session using the session id (from the client browser) ... hence no prompts to re-login.

The feature is enabled by default if you make use of Spring Dev Tools (spring-boot-devtools) in pom file. If you don't use Spring Dev tools, then you can set the properties accordingly in the application.properties file.

server.servlet.session.persistent=false # Whether to persist session data between restarts.
server.servlet.session.timeout=30m # Session timeout. If a duration suffix is not specified, seconds will be used.

The properties are mentioned in the Spring Boot Reference Manual - Customizing Embedded Servlet Containers

You can also find additional properties here:

https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html

Just search for "server.servlet.session"
```
