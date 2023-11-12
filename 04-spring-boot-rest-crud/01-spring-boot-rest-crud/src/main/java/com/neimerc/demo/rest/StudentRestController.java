package com.neimerc.demo.rest;

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.demo.entity.Student;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class StudentRestController {

  private List<Student> theStudents;

  // definir @PostConstruct para cargar la data de student ... solo una vez!
  // Por ahora, datos hardcodeados para hacerlo sencillo
  @PostConstruct
  public void loadData() {
    theStudents = new ArrayList<>();

    theStudents.add(new Student("Adriana", "López"));
    theStudents.add(new Student("José M.", "Muñoz"));
    theStudents.add(new Student("Tania", "Pérez"));
  }
  
  // definir un endpoint para "/students" - devuelve una lista de estudiantes
  @GetMapping("/students")
  public List<Student> getStudents() {
    // Jackson convierte automáticamente nuesta lista de Student a un array JSON
    return theStudents;
  }

  // definir un endpoint para "/students/{studentId}" - devuelve student por índice
  // Por defecto, el nombre del path variable y del parámetro del método deben coincidir.
  //
  // Problema, ¿Qué pasa si como studentId pasamos texto en vez de un integer? Tenemos que modificar el código
  // para gestionar estos casos límite o configurar un manejador de excepciones genérico.
  @GetMapping("/students/{studentId}")
  public Student getStudent(@PathVariable int studentId) {

    // acceder por el id a la lista ... por ahora lo mantenemos simple

    // Confirmamos que existe el studentId
    if ((studentId >= theStudents.size()) || (studentId < 0)) {
      throw new StudentNotFoundException("Student id not found - " + studentId);
    }

    return theStudents.get(studentId);
  }

  // Añadir un exception handler
  //    - Usamos la anotación @ExceptionHandler
  //    - Indicamos el tipo del Response body, en este caso StudentErrorResponse
  //    - Indicamos el tipo de la excepción que manejamos en este método, en este caso StudentNotFoundException
  @ExceptionHandler
  public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {

    // crear un StudentErrorResponse
    //    Jacson convertirá estos datos a JSON
    StudentErrorResponse error = new StudentErrorResponse();

    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setMessage(exc.getMessage());
    error.setTimestamp(System.currentTimeMillis());

    // retornar un ResponseEntity
    //    Indicamos el body (la variable error) y el status code
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
