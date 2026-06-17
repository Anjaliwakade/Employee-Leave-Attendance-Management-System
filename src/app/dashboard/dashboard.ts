import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

import { LeaveService } from '../services/leave';
import { EmployeeService } from '../services/employee';
import { AttendanceService } from '../services/attendance';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit {

  email: string = '';

  leaves: any[] = [];
  attendanceList: any[] = [];

  casualLeave: number = 0;
  sickLeave: number = 0;
  earnedLeave: number = 0;

  constructor(
    private router: Router,
    private leaveService: LeaveService,
    private employeeService: EmployeeService,
    private attendanceService: AttendanceService
  ) {}

  ngOnInit() {
    this.loadUser();
    this.loadLeaves();
    this.loadLeaveBalance();
    this.loadAttendance();

    setInterval(() => {
    this.loadLeaves();
  }, 5000);
  }

  // ---------------- USER ----------------
  loadUser() {
    const token = localStorage.getItem('token');

    if (token) {
      const decoded: any = jwtDecode(token);
      this.email = decoded.sub;
    }
  }

  // ---------------- LEAVES ----------------
  loadLeaves() {

    const employeeId = Number(
      localStorage.getItem('employeeId')
    );

    this.leaveService
      .getLeavesByEmployeeId(employeeId)
      .subscribe({

        next: (res: any) => {
          this.leaves = res;
          console.log('Leaves:', res);
        },

        error: (err) => {
          console.error(err);
        }

      });
  }

  // ---------------- LEAVE BALANCE ----------------
  loadLeaveBalance() {

    const employeeId = Number(
      localStorage.getItem('employeeId')
    );

    console.log("Employee ID:", employeeId);

    this.employeeService
      .getEmployeeById(employeeId)
      .subscribe({

        next: (emp: any) => {

          console.log("Employee Data:", emp);

          this.casualLeave = emp.casualLeave;
          this.sickLeave = emp.sickLeave;
          this.earnedLeave = emp.earnedLeave;

        },

        error: (err) => {
          console.error(err);
        }

      });
  }

  // ---------------- ATTENDANCE ----------------
  loadAttendance() {

    const employeeId = Number(
      localStorage.getItem('employeeId')
    );

    this.attendanceService
      .getAttendance(employeeId)
      .subscribe({

        next: (res: any) => {
          this.attendanceList = res;
          console.log("Attendance:", res);
        },

        error: (err) => {
          console.error(err);
        }

      });
  }

  checkIn() {

    const employeeId = Number(
      localStorage.getItem('employeeId')
    );

    this.attendanceService
      .checkIn(employeeId)
      .subscribe({

        next: () => {
          alert("Checked In Successfully");
          this.loadAttendance();
        },

        error: (err) => {
          console.error(err);
        }

      });
  }

  checkOut() {

    const employeeId = Number(
      localStorage.getItem('employeeId')
    );

    this.attendanceService
      .checkOut(employeeId)
      .subscribe({

        next: () => {
          alert("Checked Out Successfully");
          this.loadAttendance();
        },

        error: (err) => {
          console.error(err);
        }

      });
  }

  // ---------------- LOGOUT ----------------
  logout() {

    localStorage.removeItem('token');
    localStorage.removeItem('employeeId');

    this.router.navigate(['/login']);
  }
}