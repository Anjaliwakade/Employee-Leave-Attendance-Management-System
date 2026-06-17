import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../services/employee';
import { LeaveService } from '../services/leave';
import { AttendanceService } from '../services/attendance';

@Component({
selector: 'app-admin',
standalone: true,
imports: [CommonModule],
templateUrl: './admin.html',
styleUrl: './admin.css'
})
export class AdminComponent implements OnInit {

employees: any[] = [];
leaves: any[] = [];
pendingAdminLeaves: any[] = [];
attendanceList: any[] = [];

constructor(
private employeeService: EmployeeService,
private leaveService: LeaveService,
 private attendanceService: AttendanceService
) {}

ngOnInit(): void {
this.loadAllData();
}

loadAllData(): void {
this.loadEmployees();
this.loadLeaves();
this.loadPendingAdminLeaves();
}

loadEmployees(): void {
this.employeeService.getAllEmployees().subscribe({
next: (res: any) => {
this.employees = res;
console.log('Employees:', res);
},
error: (err: any) => {
console.error(err);
}
});
}

loadLeaves(): void {
this.employeeService.getAllLeaves().subscribe({
next: (res: any) => {
this.leaves = res;
console.log('Leaves:', res);
},
error: (err: any) => {
console.error(err);
}
});
}

loadPendingAdminLeaves(): void {
this.employeeService.getPendingAdminLeaves().subscribe({
next: (res: any) => {
this.pendingAdminLeaves = res;
console.log('Pending Admin Leaves:', res);
},
error: (err: any) => {
console.error(err);
}
});
}


loadAttendance() {

  this.attendanceService.getAllAttendance()
    .subscribe({
      next: (res: any) => {

        this.attendanceList = res.map((a: any) => ({

          name: a.employee?.firstName + ' ' + a.employee?.lastName,
          role: a.employee?.role,

          employeeId: a.employee?.employeeId,

          date: a.date,

          checkIn: a.checkInTime || '-',
          checkOut: a.checkOutTime || '-',
          hours: a.workingHours || 0

        }));

      },
      error: (err) => console.error(err)
    });

}

approveLeave(id: number) {

  this.leaveService.approveByAdmin(id)
    .subscribe({
      next: () => {
        alert('Leave Approved by Admin');
        this.loadPendingAdminLeaves(); // or reload function
        this.leaveService.getPendingLeaves()
      },
      error: (err) => console.error(err)
    });


}

rejectLeave(id: number): void {

  this.leaveService.rejectLeave(id)
    .subscribe({
      next: () => {

        alert('Leave Rejected');

        this.loadLeaves();
        this.loadPendingAdminLeaves();

      },
      error: (err) => {
        console.error(err);
      }
    });

}

logout(): void {
localStorage.clear();
window.location.href = '/login';
}
}
