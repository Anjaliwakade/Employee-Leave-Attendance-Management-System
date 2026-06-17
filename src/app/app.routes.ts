import { Routes } from '@angular/router';

import { LoginComponent } from './login/login';
import { DashboardComponent } from './dashboard/dashboard';
import { LeaveComponent } from './leave/leave';
import { ManagerComponent } from './manager/manager';

import { authGuard } from './guards/auth-guard';

import { AdminComponent } from './admin/admin';
import { roleGuard } from './guards/role-guard';
export const routes: Routes = [

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'EMPLOYEE' }
  },

  {
    path: 'leave',
    component: LeaveComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'EMPLOYEE' }
  },

  {
    path: 'manager',
    component: ManagerComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' }
  },

  {
  path: 'admin',
  component: AdminComponent,
  canActivate: [authGuard, roleGuard],
  data: { role: 'ADMIN' }
}

];

