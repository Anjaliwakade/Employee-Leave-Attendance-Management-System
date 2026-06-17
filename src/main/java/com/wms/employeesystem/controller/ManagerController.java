package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/team/{managerId}")
    public List<Employee> getTeam(@PathVariable Long managerId) {
        return managerService.getTeam(managerId);
    }
}