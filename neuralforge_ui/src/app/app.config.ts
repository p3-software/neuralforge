import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { baseUrlInterceptor } from './interceptors/base-url.interceptor';
import { accessTokenInterceptor } from './interceptors/access-token.interceptor';
import { handleErrorsInterceptor } from './interceptors/handle-errors.interceptor';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

/**
 * Application configuration object.
 * Defines global providers for routing, HTTP client, interceptors, and animations.
 */
export const appConfig: ApplicationConfig = {
  providers: [
    /**
     * Provides the application's routing configuration.
     */
    provideRouter(routes),

    /**
     * Enables client-side hydration for better performance in SSR applications.
     */
    provideClientHydration(),

    /**
     * Configures the HTTP client with interceptors for request handling.
     */
    provideHttpClient(
      withInterceptors([
        baseUrlInterceptor, // Adds a base URL to all API requests.
        accessTokenInterceptor, // Attaches the access token to authenticated requests.
        // handleErrorsInterceptor // (Commented out) Handles API errors globally.
      ])
    ),

    /**
     * Enables asynchronous animations for better performance.
     */
    provideAnimationsAsync()
  ]
};