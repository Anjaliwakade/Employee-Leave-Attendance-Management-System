package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByEmail(email);

        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles("USER")
                .build();
    }
}