package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getTeam(Long managerId) {
        return employeeRepository.findByManagerEmployeeId(managerId);
    }
}