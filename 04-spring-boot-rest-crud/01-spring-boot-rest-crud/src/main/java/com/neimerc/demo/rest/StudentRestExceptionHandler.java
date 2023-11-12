package com.neimerc.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentRestExceptionHandler {
  // añadir código de manejo de excepciones aquí
  
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

  // Añadir un exception handler genérico, para gestionar cualquier tipo de excepción.
  //  Utilizamos como parámetro la clase genérica Exception
  @ExceptionHandler
  public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
    // crear un StudentErrorResponse
    //    Jacson convertirá estos datos a JSON
    StudentErrorResponse error = new StudentErrorResponse();

    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimestamp(System.currentTimeMillis());

    // retornar un ResponseEntity
    //    Indicamos el body (la variable error) y el status code
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
