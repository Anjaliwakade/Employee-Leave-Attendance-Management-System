import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  private baseUrl = 'http://localhost:8080/leave';

  constructor(private http: HttpClient) {}

  applyLeave(data: any): Observable<any> {

    const token = localStorage.getItem('token');

    return this.http.post(
      `${this.baseUrl}/save`,
      data,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
  }

  getLeavesByEmployeeId(employeeId: number) {

    return this.http.get(
      `${this.baseUrl}/employee/${employeeId}`
    );
  }
  getPendingLeaves() {

  return this.http.get(
    `${this.baseUrl}/pending`
  );

}

approveLeave(id: number) {

  return this.http.put(
    `${this.baseUrl}/approve/${id}`,
    {}
  );

}

rejectLeave(id: number) {

  return this.http.put(
    `http://localhost:8080/leave/reject/${id}`,
    {}
  );

}

getLeavesByEmployee(employeeId: number) {

  return this.http.get(
    `http://localhost:8080/leave/employee/${employeeId}`
  );

}
approveByManager(id: number) {
  return this.http.put(
    `http://localhost:8080/leave/approve/manager/${id}`,
    {}
  );
}

approveByAdmin(id: number) {
  return this.http.put(
    `http://localhost:8080/leave/approve/admin/${id}`,
    {}
  );
}
getAllLeaves() {
  return this.http.get(`${this.baseUrl}/all`);
}

getManagerLeaves(managerId: number) {
  return this.http.get(
    `${this.baseUrl}/manager/${managerId}`
  );
}
getTeamLeaves(managerId: number) {
  return this.http.get(
    `http://localhost:8080/leave/manager/${managerId}`
  );
}
}