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

  @Override
  public Employee findById(int theId) {
    // obtener employee
    Employee theEmployee = entityManager.find(Employee.class, theId);

    // devolver employee
    return theEmployee;
  }

  // Si se usan la capa Service y la capa DAO juntas, las buenas prácticas 
  // indican que la anotación @Transactional se indique en la capa Service.
  @Override
  public Employee save(Employee theEmployee) {
    // grabar/actualizar employee dependiendo de si el id es 0 (grabar) o está informado (actualizar)
    Employee dbEmployee = entityManager.merge(theEmployee);

    // devolver dbEmployee
    return dbEmployee;
  }

  // Si se usan la capa Service y la capa DAO juntas, las buenas prácticas 
  // indican que la anotación @Transactional se indique en la capa Service.
  @Override
  public void deleteById(int theId) {
    // encontrar employee por el id
    Employee theEmployee = entityManager.find(Employee.class, theId);

    // eliminar employee
    entityManager.remove(theEmployee);
  }
  
}
