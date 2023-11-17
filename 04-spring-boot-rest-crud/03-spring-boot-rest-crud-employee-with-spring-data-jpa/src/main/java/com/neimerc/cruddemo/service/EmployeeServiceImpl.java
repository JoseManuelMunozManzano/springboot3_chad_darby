package com.neimerc.cruddemo.service;

import java.util.List;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neimerc.cruddemo.dao.EmployeeRepository;
import com.neimerc.cruddemo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  // Inyección en el constructor
  // @Autowired
  public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
    employeeRepository = theEmployeeRepository;
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee findById(int theId) {
    // Con Optional no tenemos que chequear null
    Optional<Employee> result = employeeRepository.findById(theId);
    Employee theEmployee = null;

    if (result.isPresent()) {
      theEmployee = result.get();
    } else {
      // we didn't find the employee
      throw new RuntimeException("Did not find employee id - " + theId);
    }

    return theEmployee;
  }

  // JPA Repository provee la funcionalidad @Transactional y no es necesario indicarlo aquí
  @Override
  public Employee save(Employee theEmployee) {
    return employeeRepository.save(theEmployee);
  }
  
  // JPA Repository provee la funcionalidad @Transactional y no es necesario indicarlo aquí
  @Override
  public void deleteById(int theId) {
    employeeRepository.deleteById(theId);
  }
  
}
