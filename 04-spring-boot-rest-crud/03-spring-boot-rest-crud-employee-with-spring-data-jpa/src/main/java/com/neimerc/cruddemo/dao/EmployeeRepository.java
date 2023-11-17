package com.neimerc.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neimerc.cruddemo.entity.Employee;

// Indicamos el Entity Type y el tipo de la Primary Key
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  
  // Ya esta... no hay necesidad de escribir ningún código
  
}
