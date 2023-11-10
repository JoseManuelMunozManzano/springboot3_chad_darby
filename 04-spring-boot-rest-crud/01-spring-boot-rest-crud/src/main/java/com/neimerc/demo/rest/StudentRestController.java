package com.neimerc.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.demo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
  
  // definir un endpoint para "/students" - devuelve una lista de estudiantes
  @GetMapping("/students")
  public List<Student> getStudents() {
    // Por ahora, datos hardcodeados para hacerlo sencillo
    List<Student> theStudents = new ArrayList<>();

    theStudents.add(new Student("Adriana", "López"));
    theStudents.add(new Student("José M.", "Muñoz"));
    theStudents.add(new Student("Tania", "Pérez"));

    // Jackson convierte automáticamente nuesta lista de Student a un array JSON
    return theStudents;
  }
}
