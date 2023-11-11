package com.neimerc.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
}
