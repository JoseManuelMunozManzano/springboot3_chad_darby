package com.jmmunoz.cruddemo.dao;

import java.util.List;

import com.jmmunoz.cruddemo.entity.Course;
import com.jmmunoz.cruddemo.entity.Instructor;
import com.jmmunoz.cruddemo.entity.InstructorDetail;

public interface AppDAO {
  
  void save(Instructor theInstructor);

  Instructor findInstructorById(int theId);

  void deleteInstructorById(int theId);
  
  InstructorDetail findInstructorDetailById(int theId);
  
  void deleteInstructorDetailById(int theId);

  List<Course> findCoursesByInstructorId(int theId);

  Instructor findInstructorByIdJoinFetch(int theId);

  void update(Instructor tempInstructor);

  void update(Course tempCourse);

  Course findCourseById(int theId);

  void deleteCourseById(int theId);

  void save(Course theCourse);

  Course findCourseAndReviewsByCourseId(int theId);

  Course findCourseAndStudentsByCourseId(int theId);
}
