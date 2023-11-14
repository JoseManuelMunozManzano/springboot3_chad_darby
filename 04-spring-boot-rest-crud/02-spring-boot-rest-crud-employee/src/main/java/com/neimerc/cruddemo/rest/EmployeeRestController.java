package com.neimerc.cruddemo.rest;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neimerc.cruddemo.entity.Employee;
import com.neimerc.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
  
  private EmployeeService employeeService;

  //@Autowired
  public EmployeeRestController(EmployeeService theEmployeeService) {
    employeeService = theEmployeeService;
  }

  // exponer "/employees" y devolver una lista de empleados
  @GetMapping("/employees")
  public List<Employee> findAll() {
    return employeeService.findAll();
  }
}
