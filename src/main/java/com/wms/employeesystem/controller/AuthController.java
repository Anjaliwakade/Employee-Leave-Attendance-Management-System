package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.EmployeeRepository;
import com.wms.employeesystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Employee employee) {

        Employee emp = employeeRepository.findByEmail(employee.getEmail());

        if (emp == null || !emp.getPassword().equals(employee.getPassword())) {
            throw new RuntimeException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(emp.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("employeeId", emp.getEmployeeId().toString());
        response.put("email", emp.getEmail());
        response.put("role", emp.getRole());


        return response;
    }

    @GetMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        if (jwtService.validateToken(token)) {
            return "Valid Token";
        }

        return "Invalid Token";
    }


}
