package com.wms.employeesystem.controller;

import java.util.List;
import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    @GetMapping("/count")
    public long getEmployeeCount() {
        return employeeService.getEmployeeCount();
    }

    @GetMapping("/manager/team/{managerId}")
    public List<Employee> getTeam(@PathVariable Long managerId) {
        return employeeService.getTeamMembers(managerId);
    }
}