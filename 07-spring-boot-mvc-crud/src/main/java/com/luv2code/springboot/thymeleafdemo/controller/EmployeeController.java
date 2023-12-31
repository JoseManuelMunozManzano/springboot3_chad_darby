package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/employees")
public class EmployeeController {
  
  private EmployeeService employeeService;

  // @Autowired es opcional ya que solo tenemos un constructor
  public EmployeeController(EmployeeService theEmployeeService) {
    employeeService = theEmployeeService;
  }

  // añadir mapeo para "/list"
  @GetMapping("/list")
  public String listEmployees(Model theModel) {

    // obtener employees de la BD
    List<Employee> theEmployees = employeeService.findAll();

    // añadir al Model de Spring
    theModel.addAttribute("employees", theEmployees);

    return "employees/list-employees";
  }

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model theModel) {
    
    // crear model attribute para enlazar la data del formulario
    Employee theEmployee = new Employee();

    theModel.addAttribute("employee", theEmployee);

    return "employees/employee-form";
  }

  @GetMapping("/showFormForUpdate")
  public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

    // obtener el employee del service
    Employee theEmployee = employeeService.findById(theId);

    // Llevar employee al model para precargar el formulario
    theModel.addAttribute("employee", theEmployee);

    // enviar a nuestro formulario
    return "employees/employee-form";
  }

  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
    
    // grabar el employee
    employeeService.save(theEmployee);

    // redirect para evitar grabar duplicados al volver a pulsar submit
    return "redirect:/employees/list";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam("employeeId") int theId) {

    // eliminar employee
    employeeService.deleteById(theId);

    // redireccionar a /employees/list
    return "redirect:/employees/list";
  }
}
