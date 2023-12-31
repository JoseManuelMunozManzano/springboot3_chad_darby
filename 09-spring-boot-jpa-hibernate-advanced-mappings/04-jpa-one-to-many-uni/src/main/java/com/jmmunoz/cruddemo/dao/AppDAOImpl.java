package com.jmmunoz.cruddemo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jmmunoz.cruddemo.entity.Course;
import com.jmmunoz.cruddemo.entity.Instructor;
import com.jmmunoz.cruddemo.entity.InstructorDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class AppDAOImpl implements AppDAO {

  // define field for entity manager
  private EntityManager entityManager;

  // inject entity manager using constructor injection
  // @Autowired       <-- No hace falta indicarlo, pero lo pongo para saber que se inyecta
  public AppDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  // Añadimos @Transactional porque estamos persistiendo la entidad.
  @Override
  @Transactional
  public void save(Instructor theInstructor) {
    // También guardará instructor_detail porque hemos indicado CascadeType.ALL
    entityManager.persist(theInstructor);
  }
  
  @Override
  public Instructor findInstructorById(int theId) {
    // También recuperará instructor_detail porque el comportamiento fetch por defecto de @OneToOne es eager
    return entityManager.find(Instructor.class, theId);
  }

  @Override
  @Transactional
  public void deleteInstructorById(int theId) {
    // recuperar instructor
    Instructor tempInstructor = findInstructorById(theId);

    // get the courses
    List<Course> courses = tempInstructor.getCourses();

    // break association of all courses for the instructor
    // Si no eliminamos al instructor de los courses --> constraint violation
    for (Course tempCourse : courses) {
      tempCourse.setInstructor(null);
    }

    // delete the instructor
    // No se eliminan los cursos asociados debido al tipo de cascada indicado
    entityManager.remove(tempInstructor);
  }

  @Override
  public InstructorDetail findInstructorDetailById(int theId) {
    return entityManager.find(InstructorDetail.class, theId);
  }

/*   @Override
  @Transactional
  public void deleteInstructorDetailById(int theId) {
    // recuperar instructor detail
    InstructorDetail tempInstructorDetail = findInstructorDetailById(theId);

    // eliminar instructor detail
    // También elimina automáticamente el objeto instructor, debido al comportamiento CascadeType.ALL.
    entityManager.remove(tempInstructorDetail);
  } */

  @Override
  @Transactional
  public void deleteInstructorDetailById(int theId) {
    // recuperar instructor detail
    InstructorDetail tempInstructorDetail = findInstructorDetailById(theId);

    // eliminar la referencia al objeto asociado
    // romper el enlace bi-direccional
    tempInstructorDetail.getInstructor().setInstructorDetail(null);

    // eliminar instructor detail (no elimina Instructor)
    entityManager.remove(tempInstructorDetail);
  }

  @Override
  public List<Course> findCoursesByInstructorId(int theId) {

    // create query
    TypedQuery<Course> query = entityManager.createQuery(
                          "from Course where instructor.id = :data", Course.class);

    query.setParameter("data", theId);

    // execute the query
    List<Course> courses = query.getResultList();

    return courses;
  }

  @Override
  public Instructor findInstructorByIdJoinFetch(int theId) {

    // create query
    // JOIN FETCH es casi igual a carga EAGER
    TypedQuery<Instructor> query = entityManager.createQuery(
                                          "select i from Instructor i "
                                        + "JOIN FETCH i.courses "
                                        + "JOIN FETCH i.instructorDetail "
                                        + "where i.id = :data", Instructor.class);

    query.setParameter("data", theId);

    // execute query
    Instructor instructor = query.getSingleResult();

    return instructor;
  }

  @Override
  @Transactional
  public void update(Instructor tempInstructor) {
    entityManager.merge(tempInstructor);
  }

  @Override
  @Transactional
  public void update(Course tempCourse) {
    entityManager.merge(tempCourse);
  }

  @Override
  public Course findCourseById(int theId) {
    return entityManager.find(Course.class, theId);
  }

  @Override
  @Transactional
  public void deleteCourseById(int theId) {
    // retrieve the course
    Course tempCourse = findCourseById(theId);

    // delete the course
    entityManager.remove(tempCourse);
  }

  @Override
  @Transactional
  public void save(Course theCourse) {
    // Guardamos el course y los reviews asociados, gracias al tipo de cascada indicado (ALL)
    entityManager.persist(theCourse);
  }

  @Override
  public Course findCourseAndReviewsByCourseId(int theId) {
    
    // create query
    // Usamos JOIN FETCH para recuperar también las reviews
    TypedQuery<Course> query = entityManager.createQuery(
              "select c from Course c "
            + "JOIN FETCH c.reviews "
            + "where c.id = :data", Course.class);

    query.setParameter("data", theId);

    // execute query
    Course course = query.getSingleResult();

    return course;
  }
}
