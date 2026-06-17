package com.wms.employeesystem.repository;

import com.wms.employeesystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeEmployeeId(Long employeeId);

    Attendance findByEmployeeEmployeeIdAndDate(Long employeeId, LocalDate date);
    long countByDateAndStatus(LocalDate date, String status);


    long countByStatus(String status);
}