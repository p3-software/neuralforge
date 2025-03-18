import { CommonModule } from '@angular/common';
import { Component, ViewChild, OnInit } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

/**
 * Login component for handling user authentication.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  public showPassword: boolean = false;

  /** Error message displayed when login fails. */
  public loginError!: string;

  /** Reference to the email input field for validation. */
  @ViewChild('email') emailModel!: NgModel;

  /** Reference to the password input field for validation. */
  @ViewChild('password') passwordModel!: NgModel;

  /** Form model for user login credentials. */
  public loginForm: { email: string; password: string } = {
    email: '',
    password: '',
  };

  /**
   * Constructor injecting necessary services.
   * @param router Router service for navigation.
   * @param authService Authentication service for handling login.
   */
  constructor(
    private router: Router, 
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Define the callback function for Google Sign-In
    (window as any).handleCredentialResponse = (response: any) => {
      this.handleGoogleLogin(response.credential);
    };
  }

  /**
   * Toggles visibility for the password input.
   */
  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  /**
   * Handles user login process, including form validation and API authentication.
   * @param event The form submit event.
   */
  public handleLogin(event: Event): void {
    event.preventDefault();
    
    // Validate email input
    if (!this.emailModel.valid) {
      this.emailModel.control.markAsTouched();
    }

    // Validate password input
    if (!this.passwordModel.valid) {
      this.passwordModel.control.markAsTouched();
    }

    // Proceed with authentication if form inputs are valid
    if (this.emailModel.valid && this.passwordModel.valid) {
      this.authService.login(this.loginForm).subscribe({
        next: () => this.router.navigateByUrl('/app/dashboard'),
        error: (err: any) => {
          this.loginError = err.error?.exception || 'An error occurred';
        }
      });
    }
  }

  /**
   * Handles Google login by sending the token to the AuthService.
   * @param token The Google ID token.
   */
  private handleGoogleLogin(token: string): void {
    console.log('Google Token:', token); // Log the token to the console
    this.authService.sendGoogleTokenToApi(token).subscribe({
      next: (response) => {
        console.log('API Response:', response);
        this.router.navigateByUrl('/app/dashboard');
      },
      error: (err) => {
        console.error('Error sending token to API:', err);
        this.loginError = 'Failed to authenticate with Google.';
      }
    });
  }
}