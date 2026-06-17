import { CanActivateFn } from '@angular/router';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';

export const roleGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {

  const router = inject(Router);

  const expectedRole = route.data['role'];

  const userRole = localStorage.getItem('role');

  if (userRole === expectedRole) {
    return true;
  }

  router.navigate(['/login']);

  return false;

};