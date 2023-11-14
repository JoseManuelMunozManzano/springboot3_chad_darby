package com.neimerc.cruddemo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  // exponer "/employees/{employeeId}"
  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee(@PathVariable int employeeId) {
    Employee theEmployee = employeeService.findById(employeeId);

    if (theEmployee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }

    return theEmployee;
  }

  // exponer "/employees" para añadir un nuevo employee
  // Hay que mandar el @RequestBody para recuperar el JSON con la información del employee a crear
  @PostMapping("/employees")
  public Employee addEmployee(@RequestBody Employee theEmployee) {
    // En caso de que se pase un id en el JSON ... ponerlo a 0.
    // Esto es para forzar a grabar un nuevo item ... en vez de actualizarlo.
    theEmployee.setId(0);

    Employee dbEmployee = employeeService.save(theEmployee);

    return dbEmployee;
  }

  // exponer "/employees" - actualizar un employee existente
  // el id se envía en la petición JSON
  @PutMapping("/employees")
  public Employee updateEmployee(@RequestBody Employee theEmployee) {
    Employee dbEmployee = employeeService.save(theEmployee);

    return dbEmployee;
  }

  // exponer "/employees/{employeeId}" - eliminar un employee
  @DeleteMapping("/employees/{employeeId}")
  public String deleteEmployee(@PathVariable int employeeId) {
    Employee tempEmployee = employeeService.findById(employeeId);

    // Lanzar una excepción si es null
    if (tempEmployee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }

    employeeService.deleteById(employeeId);

    return "Deleted employee id - " + employeeId;
  }
}
