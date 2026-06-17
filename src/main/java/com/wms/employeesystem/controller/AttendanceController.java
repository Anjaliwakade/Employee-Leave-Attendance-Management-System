package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.Attendance;
import com.wms.employeesystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin/{empId}")
    public Attendance checkIn(@PathVariable Long empId) {
        return attendanceService.markAttendance(empId);
    }

    @PostMapping("/checkout/{empId}")
    public Attendance checkOut(@PathVariable Long empId) {
        return attendanceService.checkOut(empId);
    }

    @GetMapping("/all")
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployeeId(
            @PathVariable Long employeeId) {

        return attendanceService.getAttendanceByEmployeeId(employeeId);
    }

    @GetMapping("/present/count")
    public long getPresentCountToday() {
        return attendanceService.getPresentCountToday();
    }

    @GetMapping("/employees-only")
    public List<Attendance> getEmployeesAttendanceOnly() {
        return attendanceService.getEmployeesAttendanceOnly();
    }
}