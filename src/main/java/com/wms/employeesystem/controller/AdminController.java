package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.employeesystem.entity.LeaveRequest;
import com.wms.employeesystem.service.LeaveRequestService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {

        return employeeService.getAllEmployees();

    }

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/leaves")
    public List<LeaveRequest> getAllLeaves() {

        return leaveRequestService.getAllLeaveRequests();

    }



}