import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8080/employee';

  constructor(private http: HttpClient) {}

  getEmployeeById(id: number) {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  getTeamEmployees(managerId: number) {
    return this.http.get(
      `http://localhost:8080/employee/manager/team/${managerId}`
    );
  }

  getAllEmployees() {
    return this.http.get(`${this.baseUrl}/all`);
  }

  getAllLeaves() {
    return this.http.get(`${this.baseUrl}/all`);
  }

  getPendingAdminLeaves() {
    return this.http.get(
      'http://localhost:8080/leave/pending-admin'
    );
  }
}
