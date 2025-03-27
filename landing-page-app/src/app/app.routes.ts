import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { LandingpageTeamComponent } from './pages/landingpageTeam/landingpageTeam.component';

/**
 * Application Routes
 * ------------------
 * This array defines the main navigation routes for the Angular application.
 * Each route maps a specific URL path to a component.
 */
export const routes: Routes = [
  /**
   * Default Route
   * -------------
   * Path: ''
   * This is the root route of the application.
   * It displays the main landing page (LandingPageComponent).
   */
  { path: '', component: LandingPageComponent },

  /**
   * Team Page Route
   * ---------------
   * Path: 'equipo'
   * This route displays the "Nuestro Equipo" section.
   * It renders the LandingpageTeamComponent when users navigate to '/equipo'.
   */
  { path: 'equipo', component: LandingpageTeamComponent }
];
