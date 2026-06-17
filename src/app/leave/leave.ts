import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LeaveService } from '../services/leave';

@Component({
  selector: 'app-leave',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './leave.html',
  styleUrl: './leave.css'
})
export class LeaveComponent {

  reason: string = '';
  fromDate: string = '';
  toDate: string = '';

  leaveType: string = 'CL';

  constructor(private leaveService: LeaveService) {}

applyLeave() {

  const empId = Number(localStorage.getItem('employeeId'));

  const payload = {
    leaveType: this.leaveType,
    reason: this.reason,
    fromDate: this.fromDate,
    toDate: this.toDate,
    employee: {
      employeeId: empId
    }
  };

  console.log("FINAL PAYLOAD:", payload);

  this.leaveService.applyLeave(payload).subscribe({
    next: () => alert("Leave Applied"),
    error: (err) => console.error(err)
  });
}
}