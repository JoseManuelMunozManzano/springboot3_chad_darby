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
  use `hb-01-one-to-one-uni`;
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

También vamos a añadir logs de JPA/Hibernate, en concreto, vamos a hacer el log de las sentencias SQL y de los valores de las sentencias SQL.

Esto solo para desarrollo.

```
  # Show JPA/Hibernate logging messages
  logging.level.org.hibernate.SQL=trace
  logging.level.org.hibernate.orm.jdbc.bind=trace
```

Ejecutar el proyecto para probar y veremos en la consola de ejecución de Spring el resultado `Saving instructor: ` y el instructor, seguido del texto `Done!`.

También veremos que realmente se inserta primero instructor_details (la entidad asociada) y esto es porque debido a la foreign key, la entidad instructor necesita saber el id asignado a instructor_details.

En SQuirreL, ejecutando

```
  use `hb-01-one-to-one-uni`;
  SELECT * FROM instructor;
  SELECT * FROM instructor_detail;
```

veremos que tenemos los datos del instructor.

Tras esto, la aplicación termina, ya que es una sencilla app de consola.

```
Scenario where Singleton & prototype bean scope are required

1. Singleton Bean Scope:

Default Scope: Singleton is the default scope in Spring. When you define a bean without specifying a scope, it becomes a Singleton by default.

Behavior: Singleton scope means that there will be a single instance of the bean shared across the entire Spring container.

Use Case: Use Singleton scope when you want to share a single instance of a bean throughout your application. This is suitable for stateless beans, such as services, repositories, and utility classes.

Example:

@Service
public class SingletonService {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


In this example, a SingletonService is defined as a Singleton bean. When you inject this service into multiple components, they all share the same instance.

@Controller
public class MyController {
    private final SingletonService singletonService;

    @Autowired
    public MyController(SingletonService singletonService) {
        this.singletonService = singletonService;
    }
}
Usage: In this case, any controller using SingletonService will work with the same instance, and changes to the instance's state will be visible across the entire application.



2. Prototype Bean Scope:

Behavior: Prototype scope creates a new instance of the bean every time it is requested from the Spring container.

Use Case: Use Prototype scope when you want a new instance of a bean each time it's requested. This is suitable for stateful beans, like HTTP request-specific objects or beans with mutable state.

Example:

@Component
@Scope("prototype")
public class PrototypeBean {
    private int counter = 0;

    public int increment() {
        return ++counter;
    }
}
In this example, PrototypeBean is defined as a Prototype bean with a scope annotation. When you request this bean from the Spring container, a new instance is created.

@Service
public class MyService {
    private final PrototypeBean prototypeBean;

    @Autowired
    public MyService(PrototypeBean prototypeBean) {
        this.prototypeBean = prototypeBean;
    }
}
Usage: Any service using PrototypeBean will get a new instance of PrototypeBean every time it is injected. Changes to the bean's state will not affect other instances.

---

Use Singleton scope for stateless, shared beans across the application.

Use Prototype scope for stateful, unique instances that should be created afresh each time.

---





Why we need no-arg constructor in our entities?

1. No-Arg Constructor in Entities:

JPA (Java Persistence API) Requirement: JPA implementations, including Hibernate (commonly used with Spring Data JPA), often require entities to have a no-argument constructor. This is because the JPA provider needs to create instances of the entity class during the process of retrieving entities from the database.

Proxy Creation: Additionally, proxies or dynamically generated subclasses of entities are often used for lazy loading, and these proxies require a no-arg constructor for instantiation.

@Entity
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // No-arg constructor is required by JPA
    public ExampleEntity() {
    }

    // Other constructors, getters, setters...
}


2. Autowired Dependency in Spring:

When you have a dependency (B) autowired in a class (A), Spring takes care of creating an instance of B and injecting it into A during the bean creation process.

If B has its own dependencies, Spring recursively resolves those dependencies, ensuring that the entire object graph is constructed correctly.

@Service
public class A {
    private final B b;

    @Autowired
    public A(B b) {
        this.b = b;
    }
}


Flow of Object Creation in Spring:

1. Application Startup:

Spring Boot application starts.

Spring scans the classpath for components, services, repositories, etc.

2. Component Scanning:

Spring identifies classes annotated with @Component, @Service, @Repository, etc.

It creates bean definitions for these classes.

3. Bean Creation:

For each bean definition, Spring creates an instance of the bean.

If the bean has dependencies, Spring resolves those dependencies by looking up or creating the required beans.

4. Dependency Injection:

Spring injects dependencies into the beans. This can be done through constructor injection, setter injection, or field injection, depending on the configuration.

5. Lifecycle Callbacks:

If the bean implements certain interfaces (InitializingBean, DisposableBean) or uses annotations (@PostConstruct, @PreDestroy), Spring calls the corresponding lifecycle methods.

6. Application Execution:

The application starts executing, and the beans are available for use.
```

**Encontrar instructor por id**

Crear nuevo método findInstructorById() en nuestra interfase AppDAO e implementarlo.

También recupera automáticamente el objeto instructor_details, debido a que el comportamiento fetch por defecto de un @OneToOne es eager.

En el main, se ha comentado createInstructor() y se ha creado findInstructor(appDAO)

Para probar, ejecutar el proyecto Spring Boot y obtendremos en consola los datos del instructor y de instructor_detail.

**Eliminar instructor por id**

Crear nuevo método deleteInstructorById() en nuestra interfase AppDAO e implementarlo.

También elimina automáticamente el objeto instructor_details, debido al comportamiento CascadeType.ALL.

En el main, se ha comentado createInstructor() y findInstructor() y se ha creado deleteInstructor(appDAO)

Para probar, ejecutar el proyecto Spring Boot y obtendremos en consola los datos del instructor eliminado.

Luego ejecutar las siguientes consultas SQL para comprobar que efectivamente se han borrado:

```
  use `hb-01-one-to-one-uni`;
  SELECT * FROM instructor;
  SELECT * FROM instructor_detail;
```

## 02-jpa-one-to-one-bi

Hibernate one-to-one bidireccional.

Cuando cargamos InstructorDetail también queremos obtener su asociado Instructor.

Esto no lo podemos hacer con la relación unidireccional que tenemos actualmente. Ahora solo podemos empezar obteniendo Instructor y movernos a InstructorDetail.

La solución es crear una relación bidireccional. Con ella podemos ir de Instructor a InstructorDetail o de InstructorDetail a Instructor.

Podemos mantener el esquema de BD existente, es decir, no hace falta realizar cambios en BD (no tocamos application.properties)

Lo único que tenemos que hacer es actualizar el código Java.

Proceso de desarrollo:

- Actualizar la clase InstructorDetail
  - Añadir un nuevo campo que haga referencia a Instructor
  - Añadir los métodos getter/setter para ese campo Instructor
  - Añadir la anotación @OneToOne(mappedBy="instructorDetail"), para poder señalar de nuevo a InstructorDetail desde Instructor, usando la @JoinColumn existente
- Definir la interface DAO findInstructorDetailById() y su implementación. Recupera tanto InstructorDetail como el objeto instructor, gracias al comportamiento por defecto @OneToOne
- Crear la App Main
  - Inyectamos AppDAO y creamos un método findInstructorDetail()

En el main, se ha comentado createInstructor(), findInstructor() y deleteInstructor() y se ha creado el método findInstructorDetail(appDAO)

Para probar, ejecutar el proyecto Spring Boot y obtendremos en consola los datos del instructorDetail y del instructor.

**Eliminación en Cascada**

Se va a eliminar de la entidad InstructorDetail y, gracias a la cascada, también su asociado de la entidad Instructor.

En el main, se ha comentado createInstructor(), findInstructor() y deleteInstructor(), findInstructorDetail() y se ha creado el método deleteInstructorDetail(appDAO)

Para probar, ejecutar el proyecto Spring Boot y obtendremos en consola los datos del instructor eliminado.

Luego ejecutar las siguientes consultas SQL para comprobar que efectivamente se han borrado:

```
  use `hb-01-one-to-one-uni`;
  SELECT * FROM instructor;
  SELECT * FROM instructor_detail;
```

**Solo eliminación de Instructor Detail**

Vamos a eliminar de la entidad InstructorDetail, pero manteniendo el valor asociado de la entidad Instructor.

Para esto tenemos que modificar el tipo de cascade en la entidad InstructorDetail.

Y en la implementación de deleteInstructorDetailById() tenemos que romper la referencia al objeto Instructor asociado.

`tempInstructorDetail.getInstructor().setInstructorDetail(null);`

Para probar, ejecutar el proyecto Spring Boot y obtendremos en consola los datos del instructor eliminado.

Luego ejecutar las siguientes consultas SQL para comprobar que efectivamente se ha borrado:

```
  use `hb-01-one-to-one-uni`;
  SELECT * FROM instructor;           -- De aquí NO se debe haber borrado
  SELECT * FROM instructor_detail;
```

## 03-jpa-one-to-many

One-To-Many

- Un instructor puede tener muchos cursos
- Es bidireccional

Many-To-One

- Muchos cursos pueden tener un instructor
- Lo inverso a One-To-Many

Pero un curso puede tener, como mucho, un instructor.

Requerimientos:

- Si se elimina un instructor, NO eliminiar los cursos
- Si se elimina un curso, NO eliminar el instructor

Es decir, no se aplican deletes en cascada.

Proceso de desarrollo:

- Trabajo preparatorio: Definir tablas de BD
  - Ejecutar en SQuirreL el script `00-jpa-advanced-mappings-database-scripts/hb-03-one-to-many/create-db.sql`
- Crear la clase Course
  - Anotación @ManyToOne indicando la @JoinColumn(name="instructor_id") para hacer la relación con la entity Instructor
  - No se aplica eliminación en cascada
- Actualizar la clase Instructor
  - Indicar una List<Course> y anotarla con @OneToMany(mappedBy="instructor")
  - No se aplica eliminación en cascada
  - Crear método para asignar un curso a un instructor. Necesario para mantener la bidireccionalidad
- Crear el Main
  - Modificar application.properties para apuntar al nuevo esquema

![alt text](./images/MappedBy.png)

Para probar que se guarda el instructor, su detalle y sus cursos, ejecutar el proyecto Spring Boot.

Luego ejecutar las siguientes consultas SQL para comprobar que efectivamente se ha borrado:

```
  use `hb-03-one-to-many`;
  SELECT * FROM instructor;
  SELECT * FROM instructor_detail;
  SELECT * FROM course;
```

### Eager vs Lazy

Cuando recuperamos la data, ¿debemos recuperarlo TODO?

- Eager lo recuperará todo, incluido entidades dependientes
  - Puede impactar el rendimiento si hay muchísimos registros
  - Ejemplo: Un curso puede tener más de 50.000 estudiantes y no queremos recuperar todos los estudiantes
- Lazy recupera la entidad principal y SOLO cargará bajo demanda las entidades dependientes
  - Ejemplo: Cargamos el curso, pero no los estudiantes. Estos se cargarán si los pedimos por programa
  - Requiere de una sesión Hibernate abierta (necesita una conexión a BD para recuperar data) Si está cerrada Hibernate lanzará una excepción
  - Es la mejor práctica y se recomienda usar carga perezosa

Caso de uso real

- En una vista principal de programa, usaríamos Lazy Loading
  - Solo cargamos instructores, no sus cursos
- En una vista detalle, recuperaríamos la entidad y las entidades dependientes necesarias
  - Cargamos el instructor y sus cursos

Tipo de Fetch

Al definir la relación de mapeo se puede indicar el tipo de fetch (EAGER o LAZY)

```
  @OneToMany(fetch=FetchType.LAZY, mappedBy="instructor")
  private List<Course> courses;
```

Tipos de Fetch por defecto en función del tipo de mapeo

![alt text](./images/DefaultFetchTypes.png)

Para probar que se realiza la búsqueda de forma correcta (eager o lazy) ejecutar el proyecto y ver la consola.

Veremos que falla porque la conexión está cerrada durante una carga lazy.

Soluciones:

- Indicando Eager en el campo courses vemos que funciona. Es la solución rápida, no recomendable
  - Esta solución se ha codificado con el método `findInstructorWithCourses(appDAO);`

```
  @OneToMany(mappedBy = "instructor",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                      CascadeType.DETACH, CascadeType.REFRESH})
  private List<Course> courses;
```

- Encontrar cursos de un instructor
  - Volver a dejar el fetch como LAZY (por defecto es LAZY para relaciones @OneToMany pero se indica explicitamente por legibilidad) y añadir un método a nuestro AppDAO para encontrar los cursos de un instructor. Este nuevo método se llamará desde la app Main y una vez recuperados los cursos se asociarán a su instructor

![alt text](./images/FindCoursesForInstructor.png)

- Encontrar Instructor con Cursos
  - Es una refactorización de la solución anterior, ya que esa necesita una query extra (primero encontramos el instructor y luego sus cursos)
  - Haremos un nuevo método que obtenga el instructor y los cursos, con solo una query
  - Todo esto manteniendo la carga perezosa
  - Se usará en la query JOIN FETCH (similar a carga EAGER)

![alt text](./images/FindInstructorWithCourses.png)

```
Difference between Join Fetch and Eager Loading

Both "join fetch" and "eager loading" are mechanisms to retrieve related entities from a database when querying for a particular entity. They aim is to reduce the number of database queries and improve performance by loading related data upfront. However, they work slightly differently and have different implications.


Eager Loading:

Eager loading is a fetch strategy where related entities are loaded immediately along with the main entity. In other words, when you query for an entity that has a relationship to other entities marked as "eager", JPA will automatically fetch those related entities from the database in a single query, using a join.

Pros:
- Simplifies code by loading all related data in a single query.
- Avoids the N+1 query problem (retrieving N entities results in N+1 queries to fetch related entities).

Cons:
- May lead to performance issues if the related entities have a large amount of data or if many related entities are loaded unnecessarily.
- Could result in over-fetching data if you only need a subset of related entities.


Join Fetch:

Join fetch is a keyword used in JPQL (Java Persistence Query Language) to indicate that you want to fetch related entities eagerly using a SQL join. It's a more explicit way of specifying eager loading for specific queries.

Pros:
- Provides fine-grained control over which queries should perform eager loading.
- Allows you to optimize the queries by fetching only the necessary related entities.

Cons:
- Requires more explicit query construction using JPQL.
- Might still lead to performance issues if used carelessly.


In summary, the main difference between "join fetch" and "eager loading" lies in their usage:
- Eager loading is a general configuration set on the relationship mapping in the entity classes. It applies to all queries involving that relationship unless explicitly overridden.
- Join fetch is a specific JPQL keyword used in individual queries to indicate that eager loading should be applied to that specific query only.


When deciding between the two, consider the granularity of control you need over fetching related data. If you want to eagerly fetch data for all queries involving a relationship, you can use eager loading. If you want more control over which queries perform eager loading, you can use join fetch in JPQL queries.

Keep in mind that both strategies should be used judiciously, considering the data volume, query patterns, and application performance requirements.
```
