import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guest Guard.
 * 
 * This guard prevents authenticated users from accessing routes intended for unauthenticated users (e.g., login or signup pages).
 * 
 * - If the user is **not logged in**, access is granted.
 * - If the user **is logged in**, they are redirected to the dashboard (`/app/dashboard`).
 * 
 * @returns `true` if the user is a guest (not logged in), otherwise `false` and redirects to `/app/dashboard`.
 */
export const GuestGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  // Allow access if the user is not authenticated
  if (!authService.check()) return true;

  // Redirect authenticated users to the dashboard
  router.navigateByUrl('/app/dashboard');
  return false;
};