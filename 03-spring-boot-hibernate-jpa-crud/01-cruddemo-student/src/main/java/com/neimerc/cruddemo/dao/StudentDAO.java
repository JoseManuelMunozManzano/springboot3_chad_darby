package com.neimerc.cruddemo.dao;

import java.util.List;

import com.neimerc.cruddemo.entity.Student;

// DAO (Data Access Object) es responsable de hacer de interfaz con la BBDD
public interface StudentDAO {
  
  void save(Student theStudent);

  // Indicamos Integer en vez de int porque el id podría ser null y un primitivo no puede ser null
  Student findById(Integer id);

  List<Student> findAll();
}
