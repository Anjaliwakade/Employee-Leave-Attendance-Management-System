package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.Department;
import java.util.List;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> getAllDepartments();
}