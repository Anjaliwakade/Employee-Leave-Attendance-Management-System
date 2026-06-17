package com.wms.employeesystem.repository;

import com.wms.employeesystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);

    List<Employee> findByManagerEmployeeId(Long managerId);
}

