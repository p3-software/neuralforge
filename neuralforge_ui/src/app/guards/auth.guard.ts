import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Authentication Guard.
 * 
 * This guard prevents unauthorized users from accessing protected routes. 
 * It checks whether the user is authenticated using the `AuthService`.
 * 
 * - If the user is authenticated (`authService.check()` returns `true`), access is granted.
 * - If the user is not authenticated, they are redirected to the login page (`/login`).
 * 
 * @param route - The activated route attempting to be accessed.
 * @param state - The current router state.
 * @returns `true` if the user is authenticated, otherwise `false` and redirects to `/login`.
 */
export const AuthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (authService.check()) return true;

  router.navigateByUrl('/login');
  return false;
};