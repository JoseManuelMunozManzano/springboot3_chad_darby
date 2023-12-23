package com.jmmunoz.cruddemo.dao;

import com.jmmunoz.cruddemo.entity.Instructor;
import com.jmmunoz.cruddemo.entity.InstructorDetail;

public interface AppDAO {
  
  void save(Instructor theInstructor);

  Instructor findInstructorById(int theId);

  void deleteInstructorById(int theId);
  
  InstructorDetail findInstructorDetailById(int theId);
}
