import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Route, RouterLink, RouterLinkActive } from '@angular/router';
import { LayoutService } from '../../../../services/layout.service';
import { AuthService } from '../../../../services/auth.service';
import { SvgIconComponent } from '../../../svg-icon/svg-icon.component';
import { routes } from '../../../../app.routes';

/**
 * Sidebar Component
 * 
 * This component is responsible for rendering the application's sidebar menu.
 * It dynamically determines which routes are accessible based on the user's permissions.
 */
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    SvgIconComponent
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  /** Stores the current window width to handle responsiveness */
  public width: any = window.innerWidth;
  /** Controls the visibility of the left navigation arrow */
  public showLeftArrow: boolean = true;
  /** Controls the visibility of the right navigation arrow */
  public showRigthArrow: boolean = false;
  /** Injected LayoutService to manage layout-related functionalities */
  public layoutService = inject(LayoutService);
  /** Injected AuthService to manage user authentication and permissions */
  public authService = inject(AuthService);
  /** Stores the list of permitted routes based on user roles */
  public permittedRoutes: Route[] = [];
  /** Stores the main application routes */
  public appRoutes: any;

  constructor() {
    // Retrieve the main 'app' route and filter permitted child routes based on user permissions
    this.appRoutes = routes.find(route => route.path === 'app');
    this.permittedRoutes = this.authService.getPermittedRoutes(this.appRoutes?.children || []);
  }
}