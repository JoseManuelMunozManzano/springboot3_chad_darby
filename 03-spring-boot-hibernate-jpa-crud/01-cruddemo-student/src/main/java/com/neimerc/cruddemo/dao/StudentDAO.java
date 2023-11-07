package com.neimerc.cruddemo.dao;

import com.neimerc.cruddemo.entity.Student;

// DAO (Data Access Object) es responsable de hacer de interfaz con la BBDD
public interface StudentDAO {
  
  void save(Student theStudent);
}
