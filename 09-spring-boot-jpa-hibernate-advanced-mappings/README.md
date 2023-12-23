# 09-spring-boot-jpa-hibernate-advanced-mappings

Hasta ahora, se ha cubierto en el curso un mapeo muy básico, teniendo una clase Student que se mapea usando Hibernate a una tabla student.

Vamos a ver características más avanzadas.

En aplicaciones reales tendremos:

- Muchas tablas
- Relaciones entre tablas

Y vamos a modelar esto con Hibernate.

**Mapeos avanzados**

- Uno a uno
  - Dos tablas separadas
  - ![alt text](./images/OneToOne.png)
- Uno a muchos, muchos a uno
  - ![alt text](./images/OneToMany.png)
  - En el mapeo muchos a uno, muchos cursos pueden mapearse a un instructor
- Muchos a muchos
  - ![alt text](./images/ManyToMany.png)

**Importantes Conceptos de BD**

- Primary Key
  - Identifica de forma unívoca una fila en una tabla
- Foreign Key
  - Usado para enlazar tablas
  - La utilidad principal es prevervar la relación entre tablas (integridad referencial), evitando operaciones que puedan destruir esta relación y asegurando que solo data válida es insertada en esa columna foreign key (exista en la primary key de la otra tabla)
  - Un campo en una tabla que hace referencia a la primary key de otra tabla
  - ![alt text](./images/ForeignKey.png)
- Cascada
  - Consiste en aplicar la misma operación (save, delete, ...) a entidades relacionadas
  - Ejemplo: Si eliminamos un instructor, debemos eliminar su instructor_detail (CASCADE DELETE)
  - ![alt text](./images/CascadeDelete.png)
  - Hay que tener mucho cuidado a la hora de elegir si queremos o no hacer un delete en cascada

**Tipos de Fetch: Carga Eager vs Lazy**

Cuando nos traemos/recuperamos datos, ¿debemos recuperar todos?

- Eager: recupera todo
- Lazy: recupera al hacer una petición

**Relación Unidireccional**

![alt text](./images/Unidireccional.png)

**Relación Bidireccional**

Podemos acceder a Instructor Detail usando el Instructor o al Instructor usando Instructor Detail.

![alt text](./images/Bidireccional.png)

**Proceso de desarrollo para Uno a uno**

- Trabajo preparatorio: Definir las tablas de BD
- Crear la clase InstructorDetail
- Crear la clase Instructor
- Crear la App Main

Usamos la anotación @OneToOne para relacionar tablas

![alt text](./images/AnotacionOneToOne.png)

**Ciclo de vida de un Entity usando Hibernate**

![alt text](./images/EntityLifecicle.png)

![alt text](./images/EntityLifecicleSessionMethodCalls.png)

**Tipos de Cascada**

![alt text](./images/OneToOneCascadeTypes.png)

![alt text](./images/ConfigureCascadeType.png)

**Configurar múltiples tipos de Cascada**

![alt text](./images/ConfigureMultipleCascadeTypes.png)

## 01-jpa-one-to-one-uni

Vamos a crear una app de línea de comandos con Spring Boot. La idea es enfocarnos en JPA / Hibernate y aprovechar nuestro patrón DAO.

![alt text](./images/DAOPattern.png)

**Ejecutar los scripts de BD para crear las tablas**

Los scripts están en la carpeta: `00-jpa-advanced-mappings-database-scripts`

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

Configuración de SQuirreL

![alt text](./images/SquirrelConfig.png)

La contraseña es: `springstudent`

Ejecutar el script `hb-01-one-to-one-uni/create-db.sql` en SQuirreL

Esto nos creará las tablas instructor e instructor_detail

Podemos ver como han quedado:

```
  SELECT * FROM instructor;
  SELECT * FROM instructor_detail;
```

Como es una app de consola, para no tener que estar viendo en cada ejecución el banner de Spring Boot y logs que no nos interesan (warnings y errores si que aparecen), vamos a añadir las siguientes properties:

```
  # Turn off the Spring Boot banner
  spring.main.banner-mode=off

  # Reduce logging level. Set loggin level to warn
  logging.level.root=warn
```

Ejecutar el proyecto para probar y veremos en la consola de ejecución de Spring el resultado `Hello World`

Una vez ha escrito el texto, la aplicación termina, ya que es una sencilla app de consola.
