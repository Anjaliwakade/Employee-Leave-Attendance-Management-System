package com.wms.employeesystem.service;

import com.wms.employeesystem.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.employeesystem.entity.LeaveRequest;
import com.wms.employeesystem.repository.LeaveRequestRepository;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public Map<String, Long> getAttendanceReport() {

        Map<String, Long> report = new HashMap<>();

        report.put("presentCount",
                attendanceRepository.countByStatus("PRESENT"));

        report.put("lateCount",
                attendanceRepository.countByStatus("LATE"));

        report.put("halfDayCount",
                attendanceRepository.countByStatus("HALF_DAY"));

        return report;
    }

    public List<LeaveRequest> getEmployeeLeaveReport(Long employeeId) {

        return leaveRequestRepository
                .findByEmployeeEmployeeId(employeeId);
    }

    public Map<String, Long> getLeaveStatusReport() {

        Map<String, Long> report = new HashMap<>();

        report.put("pending",
                leaveRequestRepository.countByStatus("PENDING"));

        report.put("approved",
                leaveRequestRepository.countByStatus("APPROVED"));

        report.put("rejected",
                leaveRequestRepository.countByStatus("REJECTED"));

        return report;
    }
}