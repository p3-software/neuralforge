import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

/**
 * appConfig
 * ---------
 * This is the main application configuration used for bootstrapping the Angular standalone app.
 * It defines all providers required for routing, zone change detection, and hydration.
 */
export const appConfig: ApplicationConfig = {
  providers: [
    /**
     * Enables zone change detection with event coalescing for better performance.
     * Event coalescing batches multiple events into one change detection cycle.
     */
    provideZoneChangeDetection({ eventCoalescing: true }),

    /**
     * Configures the Angular Router with application routes defined in app.routes.ts.
     */
    provideRouter(routes),

    /**
     * Enables client-side hydration support with event replay.
     * This is useful when using server-side rendering (SSR) or partial hydration.
     */
    provideClientHydration(withEventReplay())
  ]
};

