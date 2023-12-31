# 01-spring-boot-overview

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
