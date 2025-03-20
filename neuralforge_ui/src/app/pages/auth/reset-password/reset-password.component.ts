import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-password-reset',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class PasswordResetComponent implements OnInit {
  token: string | null = null; // Ahora usamos el token en lugar del userId
  newPassword: string = '';
  confirmPassword: string = '';
  validationErrors: string[] = [];
  successMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'] || null; // Obtiene el token en lugar del userId
    });
  }

  submitForm() {
    this.validationErrors = [];

    if (!this.token) {
      this.validationErrors.push("Invalid request. No token provided.");
      return;
    }

    if (!this.isPasswordValid(this.newPassword)) {
      this.validationErrors.push("Password must be at least 8 characters long and include a number and a capital letter.");
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.validationErrors.push("Passwords do not match.");
      return;
    }

    this.authService.resetPassword(this.token, this.newPassword).subscribe({
      next: () => {
        this.successMessage = "Password successfully reset. Redirecting to login...";
        setTimeout(() => this.router.navigate(['/login']), 3000);
      },
      error: () => {
        this.validationErrors.push("Failed to reset password. Please try again.");
      }
    });
  }

  private isPasswordValid(password: string): boolean {
    return /^(?=.*[A-Z])(?=.*\d).{8,}$/.test(password);
  }
}