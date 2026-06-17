package com.wms.employeesystem.controller;

import com.wms.employeesystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.employeesystem.entity.LeaveRequest;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/attendance")
    public Map<String, Long> getAttendanceReport() {

        return reportService.getAttendanceReport();
    }

    @GetMapping("/leave/employee/{employeeId}")
    public List<LeaveRequest> getEmployeeLeaveReport(
            @PathVariable Long employeeId) {

        return reportService.getEmployeeLeaveReport(employeeId);
    }
    @GetMapping("/leave/status")
    public Map<String, Long> getLeaveStatusReport() {

        return reportService.getLeaveStatusReport();
    }
}