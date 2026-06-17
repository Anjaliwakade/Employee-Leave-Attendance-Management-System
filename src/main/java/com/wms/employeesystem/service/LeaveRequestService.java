package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.LeaveRequest;
import com.wms.employeesystem.entity.Employee;
import com.wms.employeesystem.repository.LeaveRequestRepository;
import com.wms.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest) {

        if (leaveRequest.getEmployee() == null ||
                leaveRequest.getEmployee().getEmployeeId() == null) {
            throw new RuntimeException("Employee ID is required");
        }

        Long empId = leaveRequest.getEmployee().getEmployeeId();

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        leaveRequest.setEmployee(employee);

        int requestedDays = 0;

        if (leaveRequest.getFromDate() != null &&
                leaveRequest.getToDate() != null) {

            requestedDays = (int) ChronoUnit.DAYS.between(
                    leaveRequest.getFromDate(),
                    leaveRequest.getToDate()
            ) + 1;
        }

        if ("CL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

            if (employee.getCasualLeave() < requestedDays) {
                throw new RuntimeException(
                        "Insufficient Casual Leave Balance"
                );
            }
        }

        if ("SL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

            if (employee.getSickLeave() < requestedDays) {
                throw new RuntimeException(
                        "Insufficient Sick Leave Balance"
                );
            }
        }

        if ("EL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

            if (employee.getEarnedLeave() < requestedDays) {
                throw new RuntimeException(
                        "Insufficient Earned Leave Balance"
                );
            }
        }
        if ("EMPLOYEE".equalsIgnoreCase(employee.getRole())) {
            leaveRequest.setStatus("PENDING_MANAGER");
        } else if ("MANAGER".equalsIgnoreCase(employee.getRole())) {
            leaveRequest.setStatus("PENDING_ADMIN");
        }

        // ✅ applied date
        leaveRequest.setAppliedDate(LocalDate.now());

        // ✅ total days safe calculation
        if (leaveRequest.getFromDate() != null && leaveRequest.getToDate() != null) {
            long days = ChronoUnit.DAYS.between(
                    leaveRequest.getFromDate(),
                    leaveRequest.getToDate()
            );
            leaveRequest.setTotalDays((int) days + 1);
        }

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public List<LeaveRequest> getLeavesByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findByEmployeeEmployeeId(employeeId);
    }

    public List<LeaveRequest> getPendingLeaves() {
        List<LeaveRequest> allPending =
                leaveRequestRepository.findByStatus("PENDING_MANAGER");

        return allPending.stream()
                .filter(leave ->
                        leave.getEmployee() != null &&
                                "EMPLOYEE".equalsIgnoreCase(
                                        leave.getEmployee().getRole()
                                )
                )
                .toList();

    }

    public long getPendingLeaveCount() {
        return leaveRequestRepository.countByStatus("PENDING_MANAGER");
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }
    public LeaveRequest getLeaveRequestById(Long id) {

        return leaveRequestRepository.findById(id)
                .orElse(null);

    }

    public LeaveRequest approveLeave(Long id) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        Employee employee = leaveRequest.getEmployee();

        // Manager Approval
        if ("PENDING_MANAGER".equalsIgnoreCase(leaveRequest.getStatus())) {

            leaveRequest.setStatus("PENDING_ADMIN");

        }

        // Admin Final Approval
        else if ("PENDING_ADMIN".equalsIgnoreCase(leaveRequest.getStatus())) {

            if ("CL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

                employee.setCasualLeave(
                        employee.getCasualLeave()
                                - leaveRequest.getTotalDays()
                );
            }

            if ("SL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

                employee.setSickLeave(
                        employee.getSickLeave()
                                - leaveRequest.getTotalDays()
                );
            }

            if ("EL".equalsIgnoreCase(leaveRequest.getLeaveType())) {

                employee.setEarnedLeave(
                        employee.getEarnedLeave()
                                - leaveRequest.getTotalDays()
                );
            }

            employeeRepository.save(employee);

            leaveRequest.setStatus("APPROVED");
        }

        return leaveRequestRepository.save(leaveRequest);
    }



    public LeaveRequest rejectLeave(Long id) {


        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leaveRequest.setStatus("REJECTED");

        return leaveRequestRepository.save(leaveRequest);


    }

    public List<LeaveRequest> getPendingAdminLeaves() {


        return leaveRequestRepository.findByStatus("PENDING_ADMIN");


    }

    public List<LeaveRequest> getTeamLeaves(Long managerId) {
        return leaveRequestRepository.findByEmployee_Manager_EmployeeId(managerId);
    }
}