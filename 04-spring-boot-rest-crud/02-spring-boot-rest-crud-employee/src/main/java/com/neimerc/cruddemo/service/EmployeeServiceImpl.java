package com.neimerc.cruddemo.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neimerc.cruddemo.dao.EmployeeDAO;
import com.neimerc.cruddemo.entity.Employee;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeDAO employeeDAO;

  // Inyección en el constructor
  // @Autowired
  public EmployeeServiceImpl(EmployeeDAO theEmployeeDAO) {
    employeeDAO = theEmployeeDAO;
  }

  @Override
  public List<Employee> findAll() {
    return employeeDAO.findAll();
  }

  @Override
  public Employee findById(int theId) {
    // Solo delegamos las llamadas al DAO
    return employeeDAO.findById(theId);
  }

  // En la capa Service es donde se indica la transaccionalidad
  @Transactional
  @Override
  public Employee save(Employee theEmployee) {
    // Solo delegamos las llamadas al DAO
    return employeeDAO.save(theEmployee);
  }
  
  // En la capa Service es donde se indica la transaccionalidad
  @Transactional
  @Override
  public void deleteById(int theId) {
    // Solo delegamos las llamadas al DAO
    employeeDAO.deleteById(theId);
  }
  
}
