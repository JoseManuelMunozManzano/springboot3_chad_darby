package com.neimerc.cruddemo.dao;

import java.util.List;

import com.neimerc.cruddemo.entity.Employee;

public interface EmployeeDAO {
  
  List<Employee> findAll();
}
