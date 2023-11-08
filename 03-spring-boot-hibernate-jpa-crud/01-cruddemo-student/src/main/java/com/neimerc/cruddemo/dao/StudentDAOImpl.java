package com.neimerc.cruddemo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neimerc.cruddemo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// Anotación @Repository
//  Es una anotación especializada para DAO, una "sub-anotación" de @Component
//  Soporta escaneo de componentes
//  Traduce excepciones JDBC

@Repository
public class StudentDAOImpl implements StudentDAO {

  // definir campo para Entity Manager
  private EntityManager entityManager;

  // inyectar Entity Manager usando inyección en el constructor
  // Recordar que @Autowired es opcional
  //
  //@Autowired
  public StudentDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }  

  // implementar el método save
  // Se usa la anotación @Transactional (cogido de org.springframework) que gestionará la transacción
  @Override
  @Transactional
  public void save(Student theStudent) {
    entityManager.persist(theStudent);
  }

  @Override
  public Student findById(Integer id) {
    return entityManager.find(Student.class, id);
  }

  @Override
  public List<Student> findAll() {
    // crear la query
    // Se usa JPQL, basado en el nombre de la entity (no de la tabla) y de los campos de la entity (no las columnas de la tabla)
    TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student ORDER BY lastName", Student.class);

    // devolver los resultados de la query
    return theQuery.getResultList();
  }

}
