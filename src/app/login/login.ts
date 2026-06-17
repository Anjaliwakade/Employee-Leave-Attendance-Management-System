import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../services/login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {}

  onLogin() {

  if (this.email === '' || this.password === '') {
    alert('Please enter email and password');
    return;
  }

  this.loginService.login(this.email, this.password)
    .subscribe((response: any) => {

      // ================= STORE DATA =================
      localStorage.setItem('token', response.token);
      localStorage.setItem('employeeId', response.employeeId);
      localStorage.setItem('email', response.email);
      localStorage.setItem('role', response.role);

      console.log('Login Success');
      console.log('Role:', response.role);
      console.log("LOGIN RESPONSE:", response);

      // ================= ROLE BASED REDIRECT =================

      const role = response.role;

      if (role === 'ADMIN') {
        this.router.navigate(['/admin']);
      }

      else if (role === 'MANAGER') {
        this.router.navigate(['/manager']);
      }

      else if (role === 'EMPLOYEE') {
        this.router.navigate(['/dashboard']);
      }

      else {
        alert('Unknown role');
        this.router.navigate(['/login']);
      }

    });

}
}