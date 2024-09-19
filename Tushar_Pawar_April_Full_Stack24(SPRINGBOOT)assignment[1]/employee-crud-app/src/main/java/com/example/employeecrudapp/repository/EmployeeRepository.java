package com.example.employeecrudapp.repository;

import com.example.employeecrudapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
