import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../environments/environment';

/**
 * HTTP Interceptor for setting the base API URL.
 * 
 * This interceptor prepends a predefined base URL (from the environment configuration)
 * to all outgoing HTTP requests, ensuring that requests are directed to the correct API endpoint.
 * Additionally, it sets the `Accept` header to indicate that the client expects JSON responses.
 * 
 * @param req - The outgoing HTTP request.
 * @param next - The next handler in the request pipeline.
 * @returns The modified HTTP request with the base URL prepended.
 */
export const baseUrlInterceptor: HttpInterceptorFn = (req, next) => {
  const base: string = environment.apiUrl;

  // Clone the request and update the URL with the base API URL
  const clonedRequest = req.clone({
    url: `${base}/${req.url}`,
    setHeaders: {
      Accept: 'application/json',
    },
  });

  return next(clonedRequest);
};