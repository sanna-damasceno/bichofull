import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const adminGuard: CanActivateFn = () => {

  const router = inject(Router);

  const token = localStorage.getItem('token');

  if (!token) {
    router.navigate(['/login']);
    return false;
  }

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));

    if (payload.role === 'ADMIN') {
      return true;
    }

    // ❌ NÃO É ADMIN → manda pro dashboard
    router.navigate(['/dashboard']);
    return false;

  } catch (e) {
    router.navigate(['/login']);
    return false;
  }
};