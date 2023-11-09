# 02-spring-boot-spring-core

Páginas web:

```
  https://www.vojtechruzicka.com/field-dependency-injection-considered-harmful/
```

### 01-constructor-injection

Proyecto donde vamos a ver un ejemplo de Inversión de Control (IoC) e Inyección de Dependencias usando el constructor para inyectar las dependencias.

Que veremos:

- Definición de interfaz de dependencia y clase
  - Se usa la anotación @Component en la clase para marcarla como un bean de Spring
- Crear un REST Controller
  - Se usa la anotación @RestController
  - Crear un constructor para realizar la inyección de dependencias
  - Se usa la anotación @Autowired en el constructor, pero si solo tenemos un constructor, esta anotación es opcional
  - Añadir un endpoint: @GetMapping("/dailyworkout")
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 02-component-scanning

Proyecto donde vamos a ver un ejemplo de escaneo de componentes en distintos packages.

- Se ha creado una serie de packages (rest y common) dentro del package springcoredemo
  - Dentro del package springcoredemo los componentes son escaneados automáticamente por Spring Boot
- Se ha creado un package llamado util fuera del package springcoredemo
  - Si llevo los fuentes de springcoredemo/common a /util, veo que no se realiza el escaneo de componentes y falla
  - Para que Spring Boot escanee el package util hay que indicarselo de la siguiente forma:
    ```
      @SpringBootApplication(
      	scanBasePackages = {"com.neimerc.springcoredemo", "com.neimerc.util"}
      )
    ```

### 03-setter-injection

Proyecto donde vamos a ver un ejemplo de Inversión de Control (IoC) e Inyección de Dependencias usando el método setter para inyectar las dependencias.

Que veremos:

- Crear un método setter en la clase controller para realizar la inyección
- Configurar la inyección de dependencia usando la anotación @Autowired
- Indicando @Autowired, se puede inyectar una dependencia en cualquier método, no necesariamente un setter
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 04-qualifiers

Proyecto donde vemos como gestionar la inyección de dependencias cuando hay varias implementaciones de una interface.

- Veremos la anotación @Qualifier para ser específico sobre que bean inyectar
- Veremos esta anotación tanto para inyección usando el constructor como usando un setter
- La forma de usarla es: `@Qualifier("miClase")` donde el primer carácter (m) es en minúsculas
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 05-primary

Proyecto donde vemos una solución alternativa para gestionar la inyección de dependencias cuando hay varias implementaciones de una interface.

- Cuando no nos importa que bean se va a inyectar, cogemos la que tenga la anotación @Primary
- Solo una clase de las que implementan la interface puede tener la anotación @Primary
- Con esto, se hace innecesario usar la anotación @Qualifier en el controlador
- Es posible mezclar el uso de @Qualifier y @Primary, solo hay que tener en cuenta que @Qualifier tiene prioridad
- En general, se recomienda usar @Qualifier porque es más específica sobre que bean se va a inyectar
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 06-lazy-initialization

Proyecto donde vemos como hacer que los beans no se inicialicen al ejecutarse la aplicación.

- Por defecto, al comenzar la aplicación, todos los beans son inicializados
- Es decir, Spring crea una instancia de cada uno de ellos (anotados con @Component...) para que estén disponibles
- Probaremos que se inician usando getClass().getSimpleName() en el constructor de cada implementación de la interface Coach
- Usando lazy initialization, un bean se inicializará solo en los siguientes casos:
  - Si se necesita por una inyección de dependencia
  - Si es requerido explicitamente
- Un bean tendrá lazy initialization si se usa la anotación @Lazy en la implementanción del bean
- Como puede haber muchas clases, se puede indicar lazy initialization como una propiedad de configuración global
  - spring.main.lazy-initialization=true
  - Todas las clases, incluido nuestro controlador (tiene la anotación @RestController) son lazy
- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout`

### 07-bean-scopes

Proyecto donde vemos el ciclo de vida de un bean, cuánto vive un bean, cuántas intancias se crean y como se comparte.

- Podemos cambiar el scope en cada una de las clases (beans) que implementa la interface Coach de la siguiente forma:
- El scope por defecto es singleton. Crea una instancia compartida
  - @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
- El scope prototype crea una nueva instancia de bean en cada inyección realizada
  - @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  - IMPORTANTE: Los bean con scope prototype son lazy por defecto. No hace falta usar @Lazy
- El scope request se usa solo para aplicaciones web. Alcance a una solicitud web HTTP
- El scope session se usa solo para aplicaciones web. Alcance a una sesión web HTTP
- El scope global-session se usa solo para aplicaciones web. Alcance a una sesión web HTTP global
- Testear en la siguiente ruta: `http://localhost:8080/check`

### 08-bean-lifecycle-methods

Proyecto donde vemos como añadir código personalizado durante la inicialización de un bean y durante su destrucción.

- Podemos añadir código para:
  - Llamar a métodos personalizados que contengan lógica de negocio
  - Configuraciones/Limpieza de recursos tales como db, sockets, ficheros...
- Para añadir este código, en las clases que implementan la interface Coach se añade la anotación:
  - @PostConstruct seguida del método que contiene la lógica de lo que se quiera hacer después de crearse el bean
  - @PreDestroy seguida del método que contiene la lógica de lo que se quiera hacer antes de destruirse el bean
- Testear en la consola de Spring al arrancar y parar el proyecto
- IMPORTANTE: Para un bean con scope prototype, Spring no llama al método destroy

### 09-java-config-bean

Proyecto donde, en vez de usar la anotación @Component (u otra) para indicar que es un bean de Spring, se configura via código Java.

- Hay que crear una clase de configuración e indicar la anotación @Configuration
- Se crean métodos de creación de instancia a los que se le indica la anotación @Bean
  - Si llamamos al método swimCoach entonces el id del bean, por defecto, será swimCoach
  - Se puede cambiar el id del bean indicándolo en la misma anotación @Bean, por ejemplo: @Bean("aquatic")
- Ya podemos inyectar el bean en nuestro controlador usando, si fuera necesario, la anotación @Qualifier y el id del bean
  - @Qualifer("swimCoach") o @Qualifier("aquatic"), dependiendo del id del bean que hayamos indicado

¿Cuándo se usan estas clases de configuración de beans de Spring?

- Cuando queremos hacer disponible para Spring una clase de terceros, ya que no tenemos acceso al código fuente

- Testear en la siguiente ruta: `http://localhost:8080/dailyworkout` y también mirando la consola de Spring
