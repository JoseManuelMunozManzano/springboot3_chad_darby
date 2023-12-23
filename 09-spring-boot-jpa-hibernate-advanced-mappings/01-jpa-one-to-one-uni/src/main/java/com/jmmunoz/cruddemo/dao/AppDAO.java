package com.jmmunoz.cruddemo.dao;

import com.jmmunoz.cruddemo.entity.Instructor;

public interface AppDAO {
  
  void save(Instructor theInstructor);

  Instructor findInstructorById(int theId);

  void deleteInstructorById(int theId);
}
