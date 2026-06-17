import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  private baseUrl =
    'http://localhost:8080/attendance';

  constructor(
    private http: HttpClient
  ) {}

  checkIn(employeeId: number) {

    return this.http.post(
      `${this.baseUrl}/checkin/${employeeId}`,
      {}
    );

  }

  checkOut(employeeId: number) {

    return this.http.post(
      `${this.baseUrl}/checkout/${employeeId}`,
      {}
    );

  }

  getAttendance(employeeId: number) {

    return this.http.get(
      `${this.baseUrl}/employee/${employeeId}`
    );

  }
  getAllAttendance() {
  return this.http.get(
    'http://localhost:8080/attendance/all'
  );
}
}