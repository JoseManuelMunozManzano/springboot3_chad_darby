package com.neimerc.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.neimerc.cruddemo.entity.Employee;

// En vez de http://localhost:8080/magic-api/employees ahora el path es http://localhost:8080/magic-api/members
// @RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  
  // Ya esta... no hay necesidad de escribir ningún código
  
}
