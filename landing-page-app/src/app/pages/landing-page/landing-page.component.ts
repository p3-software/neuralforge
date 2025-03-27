import { Component } from '@angular/core';

/**
 * LandingPageComponent
 * ---------------------
 * This is the main landing page component of the application.
 * It uses an external HTML template and SCSS stylesheet to structure
 * and style the landing page content.
 *
 * The component is defined as standalone, which means it does not require
 * declaration in an Angular module and can import other standalone components directly.
 */
@Component({
  selector: 'app-landing-page',            // HTML tag to use this component
  standalone: true,                        // Enables standalone mode
  imports: [],                             // Array to include standalone components/modules
  templateUrl: './landing-page.component.html',  // External HTML template
  styleUrl: './landing-page.component.scss'      // External SCSS styles
})
export class LandingPageComponent {
  // This component currently has no logic inside the class.
}
