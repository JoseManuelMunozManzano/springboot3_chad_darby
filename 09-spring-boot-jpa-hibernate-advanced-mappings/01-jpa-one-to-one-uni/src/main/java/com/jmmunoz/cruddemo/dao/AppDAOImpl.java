package com.jmmunoz.cruddemo.dao;

import org.springframework.stereotype.Repository;

import com.jmmunoz.cruddemo.entity.Instructor;

import jakarta.persistence.EntityManager;
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
  
}
