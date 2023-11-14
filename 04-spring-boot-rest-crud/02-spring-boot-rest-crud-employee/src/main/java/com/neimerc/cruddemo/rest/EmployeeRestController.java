package com.neimerc.cruddemo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.cruddemo.dao.EmployeeDAO;
import com.neimerc.cruddemo.entity.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
  
  private EmployeeDAO employeeDAO;

  // rápido y sucio: inyectar employee dao usando inyección en el constructor.
  // Luego lo refactorizaremos en la capa service
  public EmployeeRestController(EmployeeDAO theEmployeeDAO) {
    employeeDAO = theEmployeeDAO;
  }

  // exponer "/employees" y devolver una lista de empleados
  @GetMapping("/employees")
  public List<Employee> findAll() {
    return employeeDAO.findAll();
  }
}
