package com.neimerc.cruddemo.rest;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;

import com.neimerc.cruddemo.entity.Employee;
import com.neimerc.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
  
  private EmployeeService employeeService;

  // ObjectMapper esta preconfigurado por SpringBoot y es fácilmente inyectable.
  // Sirve para pasar de JSON a objeto y al revés.
  private ObjectMapper objectMapper;

  //@Autowired
  public EmployeeRestController(EmployeeService theEmployeeService, ObjectMapper theObjectMapper) {
    employeeService = theEmployeeService;
    objectMapper = theObjectMapper;
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

  // añadir mapeo para PATCH "/employees/{employeeId}" - patch employee ... actualización parcial
  @PatchMapping("/employees/{employeeId}")
  public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload) {
    Employee tempEmployee = employeeService.findById(employeeId);

    // throw exception if null
    if (tempEmployee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }

    // throw exception if request body contains "id" key
    if (patchPayload.containsKey("id")) {
      throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
    }

    Employee patchedEmployee = apply(patchPayload, tempEmployee);

    Employee dbEmployee = employeeService.save(patchedEmployee);

    return dbEmployee;
  }

  private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
    // Convertir el objeto employee a un node JSON object
    ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

    // Convertir map pathPayload a un node JSON object
    ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

    // Unir las actualizaciones patch en el node employee
    employeeNode.setAll(patchNode);

    // Convertir node JSON object de vuelta a un objeto Employee
    return objectMapper.convertValue(employeeNode, Employee.class);
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
