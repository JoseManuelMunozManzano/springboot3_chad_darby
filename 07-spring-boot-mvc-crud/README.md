# 07-spring-boot-mvc-crud

Se va a realizar un proyecto MVC CRUD usando Thymeleaf y Spring Boot.

![alt text](./images/MVCCrudProject.png)

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

Ejecutar las consultas del archivo `sql-scripts/employee-directory.sql` para crear una nueva tabla de BBDD llamada employee y poblarla de datos de prueba.

Para testear, ejecutar el proyecto e ir a la url: `http://localhost:8080/api/employees`
