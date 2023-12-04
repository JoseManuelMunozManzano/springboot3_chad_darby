package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

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

    return "list-employees";

  }
}
