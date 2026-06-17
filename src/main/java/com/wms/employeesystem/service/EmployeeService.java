package com.wms.employeesystem.service;

import java.util.List;
import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public long getEmployeeCount() {
        return employeeRepository.count();
    }

    public List<Employee> getTeamMembers(Long managerId) {
        return employeeRepository.findByManagerEmployeeId(managerId);
    }
}