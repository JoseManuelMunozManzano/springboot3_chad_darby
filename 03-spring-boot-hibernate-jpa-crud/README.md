# 03-spring-boot-hibernate-jpa-crud

Hibernate maneja todo el SQL de bajo nivel y minimiza la cantidad de código JDBC que tenemos que desarrollar.

Hibernate también ofrece ORM (Object-to-Relational Mapping)

El desarrollador define el mapeo entre la clase de Java y la tabla en BBDD. Este mapeo se configura via fichero XML o anotaciones.

JPA (Jakarta Persistence API) es una especificación, la API estándar para ORM.

Define un conjunto de interfaces y requiere una implementación para ser usada (Hibernate es una implementación de JPA)

Los beneficios de usar JPA es que no estamos limitados por la implementación de un proveedor y podemos mantener un código flexible y portable implementando las interfaces de JPA.

El cambio de un proveedor a otro conllevará un mínimo de cambio de código porque estamos escribiendo código para la implementación estandar.

Hibernate / JPA usa JDBC para todas las comunicaciones de BBDD, es decir, Hibernate / JPA es otra capa de abstracción por encima de JDBC.

- `EntityManager` es un componente especial de JPA que se usa para crear queries...
- Basado en las configuraciones, Spring Boot creará automáticamente los beans: DataSource, EntityManager...
- Podremos entonces inyectarlas en la app, por ejemplo en nuestros DAO
- Para guardar en BD: `entityManager.persist(mi_objeto);`
- Para recuperar de BD usando la primary key: `Student myStudent = entityManager.find(Student.class, theID)`
- Hay otras formas de recuperar objetos de BD...

### 01-cruddemo-student

Para el proyecto se usa MariaDB y hay que ejecutar para crear el usuario y las tablas de trabajo los siguientes scripts, que se encuentran en la carpeta 00-starter-sql-scripts:

- 01-create-user.sql
- 02-student-tracker.sql

Nota: Yo he usado una imagen de Docker de MariaDB. El comando que he utilizado ha sido el siguiente:

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

Para ver la información que vayamos guardando en esta app ejecutar: `select * from student_tracker.student;`

- Configurar el proyecto con Spring Initialzr (yo uso VSCode para ello)
- Añadir dependencias al POM:
  - MariaDB Driver: `mariadb-java-client`
  - Spring Data JPA: `spring-boot-starter-data-jpa`
- Añadir la configuración de conexión a BD en el fichero `application.properties`
  ```
    spring.datasource.url=jdbc:mariadb://localhost:3306/student_tracker
    spring.datasource.username=springstudent
    spring.datasource.password=springstudent
  ```
- No hace falta indicar el nombre del driver JDBC porque Spring Boot lo detectará automáticamente, basado en la URL

Para empezar sencillo, y poder centrarnos en Hibernate/JPA, se hará una app de línea de comandos con Spring Boot.
Para ello:

```
  @Bean
  public CommandLineRunner commandLineRunner(String[] args) {
    return runner -> {
      System.out.println("Hello World!");
    };
  }
```

Este método se crea en nuestro fuente main y se ejecuta tras haberse cargado nuestros Spring Beans.

- Para probar, ejecutar el proyecto y se debe ver el texto Hello World! en la terminal de ejecución

Para quitar el banner de Spring Boot y reducir el nivel de logging para mostrar solo warnings y errores (esto es porque es una app muy pequeña donde queremos ver el resultado en el terminal), en el fichero application.properties añadir lo siguiente:

```
  spring.main.banner-mode=off
  logging.level.root=warn
```

**Proceso de Desarrollo JPA**

1. Anotar Clase Java
2. Desarrollar código Java para realizar operaciones de BD

- Entity Class: Clase Java que está mapeada a una tabla de BD. Usamos ORM (Object-to-Relational Mapping)
  - Debe ser anotada con @Entity
  - Debe tener un constructor public o protected sin argumentos. La clase puede tener otros constructores
- Con respecto a las anotaciones Java
  - Mapeamos la clase a una tabla de BD
  - Mapeamos los campos a columnas de la BD

Ejemplo:

```
  @Entity
  @Table(name="student")
  public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    ...
  }
```

**NOTAS**
La anotación @Column es opcional. Si no se especifica, el nombre de la columna es el mismo que el del campo Java. Pero se recomienda indicarlo siempre.
Igual para la anotación @Table, si no se indica el nombre de la tabla en BD es el mismo que el de la clase. Mejor ser explícito e indicarlo.

Se indica @GeneratedValue(strategy=GenerationType.IDENTITY) para generar los id.
Si es necesaria una estrategia de generación de ids personalizada, también se puede. Hay que crear una implementación personalizada de la interface `org.hibernate.id.IdentifierGenerator` y hacer override al método `public Serializable generate(...)`

**Grabar un Objeto Java con JPA**

Vamos a hacer un CRUD application, es decir, Create, Read, Update y Delete.

Para ellos usaremos un DAO (Data Access Object) que es responsable de hacer de interfaz con la BBDD. Es un patrón de diseño muy común.

Crearemos métodos:

- save()
- findById()
- findAll()
- findByLastName()
- update()
- delete()
- deleteAll()

Nuestro DAO necesita un JPA Entity Manager, que es un componente principal para guardar/recuperar entidades.

Nuestro JPA Entity Manager necesita un Data Source. La Data Source define la información para la conexión con la BBDD.

Tanto el JPA Entity Manager como el Data Source son creados automáticamente por Spring Boot, basado en la información especificada en el fichero application.properties (JDBC URL, user id, password, ...)

Podemos inyectar (autowired) el JPA Entity Manager en nuestro Student DAO.

```
  Student DAO   <------>   Entity Manager   <------>   Data Source   <------>   BBDD
```

Para realizar todo esto, los pasos son:

- Definir nuestra interface DAO
- Definir la implementación del DAO, inyectando el Entity Manager
- Actualizar nuestra app main

Otras cosas necesarias son:

- Spring proporciona la anotación @Transactional. Automáticamente comienza y termina una transacción para el código JPA. No hay necesidad de hacerlo explicitamente en el código

Anotaciones especiales para DAO

- @Repository, que sería como una "sub-anotación" de @Component pero aplicada a implmentaciones DAO
  - Spring registrará automáticamente la implementación DAO gracias al escaneo de componentes
  - Spring también traducirá cualquier excepción checked de JDBC a excepciones unchecked

En cuanto a nuestro app main

- Inyectaremos StudentDAO en nuestro método commandLineRunner y crearemos otro método para crear un Student usando new, grabarlo y mostrarlo

**Cambiar el index autoincremental para que empiece en otro número**

```
  ALTER TABLE student_tracker.student AUTO_INCREMENT=3000;
```

Si volvemos a ejecutar la app, y seleccionamos en SQuirreL todos los student, veremos que el index ha pasado a empezar en el 3000

Si ahora queremos resetear los valores del AUTO_INCREMENT a 1

```
  TRUNCATE student_tracker.student;
```

Con truncate se elimina toda la data de la tabla de BD y se resetea AUTO_INCREMENT para que empiece en 1 de nuevo.

**Recuperar/Leer un objeto con JPA**

En este ejemplo vamos a leer de la tabla de BD usando la primary key, recuperando el id 1.

Usamos el objeto entityManager y la clase Entity de la siguiente manera: `Student myStudent = entityManager.find(Student.class, 1);`

Si no encontrara un registro con el id 1 devuelve null.

Los pasos para codificar esta lectura con JPA consisten en:

- Añadir un nuevo método a nuestra interface DAO
- Implementar este nuevo método en nuestra implementación del DAO (No hace falta @Transactional en lecturas)
- Actualizar nuestra app main

**Hacer queries de varios objetos**

JPA tiene un lenguaje de queries (JPQL) para recuperar objetos, similar en concepto a SQL, con la diferencia que JPQL es basado en nombres de entidades y campos de entidades en vez de nombres de tablas y columnas de tablas.

Usamos el objeto entityManager y la Entity (clase, nombre y campos) de la siguiente manera:

```
  TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student WHERE lastName='Munoz'", Student.class);
  List<Student> students = theQuery.getResultList();
```

Se pueden usar parámetros de nombre en las consultas JPQL de la siguiente forma:

```
  TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student WHERE lastName=:theData", Student.class);
  theQuery.setParameter("theData", "Munoz");
  List<Student> students = theQuery.getResultList();
```

Los pasos para codificar estas sentencias JPQL usando JPA consisten en:

- Añadir un nuevo método a nuestra interface DAO
- Implementar este nuevo método en nuestra implementación del DAO (No hace falta @Transactional en lecturas)
- Actualizar nuestra app main
