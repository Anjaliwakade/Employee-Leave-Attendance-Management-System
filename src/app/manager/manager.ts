import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { LeaveService } from '../services/leave';
import { AttendanceService } from '../services/attendance';
import { EmployeeService } from '../services/employee';

@Component({
  selector: 'app-manager',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './manager.html',
  styleUrl: './manager.css',
})
export class ManagerComponent implements OnInit {

  pendingLeaves: any[] = [];
  attendanceList: any[] = [];
  employees: any[] = [];
  allLeaves: any[] = [];

  leave = {
    leaveType: '',
    reason: '',
    fromDate: '',
    toDate: ''
  };

  constructor(
    private leaveService: LeaveService,
    private attendanceService: AttendanceService,
    private employeeService: EmployeeService
  ) {}

  // ================= INIT =================
  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.loadPendingLeaves();
    this.loadAttendance();
    this.loadEmployees();
    this.loadMyLeaves();
  }

  // ================= LEAVES =================
loadPendingLeaves() {

  const managerId = Number(localStorage.getItem('employeeId'));

  this.leaveService.getTeamLeaves(managerId)
    .subscribe({
      next: (res: any) => {
        this.pendingLeaves = res;
      },
      error: (err: any) => console.error(err)
    });

}

  approveLeave(id: number) {

    this.leaveService.approveByManager(id)
      .subscribe({
        next: () => {
          alert('Sent to Admin for approval');
          this.loadPendingLeaves();
        },
        error: (err) => console.error(err)
      });
  }

  rejectLeave(id: number) {

    this.leaveService.rejectLeave(id)
      .subscribe({
        next: () => {
          alert('Leave Rejected');
          this.loadPendingLeaves();
        },
        error: (err) => console.error(err)
      });
  }

  // ================= EMPLOYEES =================
  loadEmployees() {

    const managerId = Number(localStorage.getItem('employeeId'));

    this.employeeService.getTeamEmployees(managerId)
      .subscribe({
        next: (res: any) => this.employees = res,
        error: (err) => console.error(err)
      });
  }

  // ================= ATTENDANCE =================
  loadAttendance() {

    const managerId = Number(localStorage.getItem('employeeId'));

    this.attendanceService.getAllAttendance()
      .subscribe({
        next: (res: any) => {
          this.attendanceList = res.filter(
            (a: any) => a.employee?.employeeId !== managerId
          );
        },
        error: (err) => console.error(err)
      });
  }

  // ================= MY LEAVES =================
loadMyLeaves() {

  const empId = Number(localStorage.getItem('employeeId'));

  this.leaveService.getLeavesByEmployee(empId)
    .subscribe({
      next: (res: any) => {

        // optional clean sorting
        this.allLeaves = res.sort((a: any, b: any) =>
          new Date(b.fromDate).getTime() - new Date(a.fromDate).getTime()
        );

      },
      error: (err: any) => console.error(err)
    });
}

  // ================= CHECK IN =================
  checkIn() {

    const empId = Number(localStorage.getItem('employeeId'));

    this.attendanceService.checkIn(empId)
      .subscribe({
        next: () => {
          alert('Check-in done');
          this.loadAttendance();
        },
        error: (err) => console.error(err)
      });
  }

  // ================= CHECK OUT =================
  checkOut() {

    const empId = Number(localStorage.getItem('employeeId'));

    this.attendanceService.checkOut(empId)
      .subscribe({
        next: () => {
          alert('Check-out done');
          this.loadAttendance();
        },
        error: (err) => console.error(err)
      });
  }

  // ================= APPLY LEAVE =================
  submitLeave() {

    const empId = Number(localStorage.getItem('employeeId'));

    this.leaveService.applyLeave({
      ...this.leave,
      employee: { employeeId: empId }
    }).subscribe({
      next: () => {
        alert('Leave Applied');

        this.leave = {
          leaveType: '',
          reason: '',
          fromDate: '',
          toDate: ''
        };

        this.loadPendingLeaves();
      },
      error: (err) => console.error(err)
    });
  }

  // ================= LOGOUT =================
  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }
}