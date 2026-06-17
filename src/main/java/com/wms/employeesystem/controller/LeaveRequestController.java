package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.LeaveRequest;
import com.wms.employeesystem.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "*")
public class LeaveRequestController {


    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/save")
    public LeaveRequest saveLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.saveLeaveRequest(leaveRequest);
    }

    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployeeId(@PathVariable Long employeeId) {
        return leaveRequestService.getLeavesByEmployeeId(employeeId);
    }

    // Manager Pending Leaves
    @GetMapping("/pending")
    public List<LeaveRequest> getPendingLeaves() {
        return leaveRequestService.getPendingLeaves();
    }

    // Admin Pending Leaves
    @GetMapping("/pending-admin")
    public List<LeaveRequest> getPendingAdminLeaves() {
        return leaveRequestService.getPendingAdminLeaves();
    }

    @GetMapping("/pending/count")
    public long getPendingLeaveCount() {
        return leaveRequestService.getPendingLeaveCount();
    }

    @GetMapping("/{id}")
    public LeaveRequest getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @PutMapping("/approve/{id}")
    public LeaveRequest approveLeave(@PathVariable Long id) {
        return leaveRequestService.approveLeave(id);
    }

    @PutMapping("/reject/{id}")
    public LeaveRequest rejectLeave(@PathVariable Long id) {
        return leaveRequestService.rejectLeave(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return "Leave Request Deleted Successfully";
    }

    @GetMapping("/manager/{managerId}")
    public List<LeaveRequest> getManagerTeamLeaves(@PathVariable Long managerId) {
        return leaveRequestService.getTeamLeaves(managerId);
    }


}
