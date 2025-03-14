import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, of } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * HTTP Interceptor for handling API errors.
 * 
 * This interceptor captures HTTP errors and manages user authentication state.
 * It logs the user out and redirects to the login page when unauthorized (401/403),
 * handles validation errors (422), and handles not found errors (404).
 * 
 * @param req - The outgoing HTTP request.
 * @param next - The next handler in the request pipeline.
 * @returns The modified HTTP response, handling errors appropriately.
 */
export const handleErrorsInterceptor: HttpInterceptorFn = (req, next) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);

  return next(req).pipe(
    catchError((error: any): Observable<any> => {
      // If the response is 401 (Unauthorized) or 403 (Forbidden) and not an auth request,
      // log the user out and redirect to the login page.
      if ((error.status === 401 || error.status === 403) && !req.url.includes('auth')) {
        authService.logout();
        router.navigateByUrl('/login');
        return of({ status: false });
      }
      
      // If the response is 422 (Unprocessable Entity), throw the error response.
      if (error.status === 422) {
        throw error.error;
      }
      
      // If the response is 404 (Not Found), return a generic error response.
      if (error.status === 404) {
        throw { status: false };
      }

      // Default fallback for other error statuses.
      return of({ status: false });
    })
  );
};