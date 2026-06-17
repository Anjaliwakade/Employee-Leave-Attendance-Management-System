package com.wms.employeesystem.repository;

import com.wms.employeesystem.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatus(String status);
    long countByStatus(String status);
    List<LeaveRequest> findByEmployee_Manager_EmployeeId(Long managerId);

}