import { Component } from '@angular/core';

/**
 * Component for displaying an "Access Denied" message when a user 
 * tries to access a restricted page without proper authorization.
 */
@Component({
  selector: 'app-access-denied', // Defines the component's selector for usage in templates.
  standalone: true, // Marks this component as standalone (does not require a module).
  imports: [], // No additional imports required for this component.
  templateUrl: './access-denied.component.html', // Path to the component's HTML template.
  styleUrl: './access-denied.component.scss' // Path to the component's styles.
})
export class AccessDeniedComponent {
  // This component currently has no logic, it only serves as a placeholder 
  // for the "Access Denied" UI message.
}