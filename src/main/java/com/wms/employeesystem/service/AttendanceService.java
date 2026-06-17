package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.Attendance;
import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.AttendanceRepository;
import com.wms.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Attendance markAttendance(Long empId) {

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance existingAttendance =
                attendanceRepository.findByEmployeeEmployeeIdAndDate(
                        empId,
                        LocalDate.now()
                );

        if (existingAttendance != null) {
            throw new RuntimeException("Attendance already marked for today");
        }

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());

        LocalTime checkInTime = LocalTime.now();

        attendance.setCheckInTime(checkInTime);

        if (checkInTime.isAfter(LocalTime.of(9, 30))) {
            attendance.setStatus("LATE");
        } else {
            attendance.setStatus("PRESENT");
        }

        return attendanceRepository.save(attendance);
    }
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }

    public long getPresentCountToday() {
        return attendanceRepository.countByDateAndStatus(
                LocalDate.now(),
                "PRESENT"
        );
    }


    public Attendance checkOut(Long empId) {

        Attendance attendance =
                attendanceRepository.findByEmployeeEmployeeIdAndDate(
                        empId,
                        LocalDate.now()
                );

        if (attendance == null) {
            throw new RuntimeException("Check-In not found");
        }

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Already checked out");
        }

        LocalTime checkOutTime = LocalTime.now();

        attendance.setCheckOutTime(checkOutTime);

        Duration duration =
                Duration.between(
                        attendance.getCheckInTime(),
                        checkOutTime
                );

        double hours = duration.toMinutes() / 60.0;

        attendance.setWorkingHours(
                Math.round(hours * 100.0) / 100.0
        );

        if (hours < 8) {
            attendance.setStatus("HALF_DAY");
        }
        else if (!attendance.getStatus().equals("LATE")) {
            attendance.setStatus("PRESENT");
        }

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getEmployeesAttendanceOnly() {

        return attendanceRepository.findAll()
                .stream()
                .filter(a -> !a.getEmployee().getRole().equalsIgnoreCase("MANAGER"))
                .toList();
    }
}