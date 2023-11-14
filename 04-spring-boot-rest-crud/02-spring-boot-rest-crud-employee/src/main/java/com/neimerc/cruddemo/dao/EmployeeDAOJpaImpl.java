package com.neimerc.cruddemo.dao;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neimerc.cruddemo.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

  // definir campo para entitymanager
  private EntityManager entityManager;

  // configurar inyección en constructor
  // Recordar que @Autowired no es obligatorio indicarlo
  // @Autowired
  public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
    entityManager = theEntityManager;
  }

  @Override
  public List<Employee> findAll() {
    // crear query
    TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);

    // ejecutar query y obtener la lista de resultado
    List<Employee> employees = theQuery.getResultList();

    // devolver el resultado
    return employees;
  }
  
}
