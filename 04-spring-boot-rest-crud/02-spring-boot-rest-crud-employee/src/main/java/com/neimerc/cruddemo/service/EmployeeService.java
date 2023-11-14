package com.neimerc.cruddemo.service;

import java.util.List;

import com.neimerc.cruddemo.entity.Employee;

public interface EmployeeService {
  
  List<Employee> findAll();
  
}
