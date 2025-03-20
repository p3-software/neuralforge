import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  public showPassword: boolean = false;
  public loginError!: string;

  @ViewChild('email') emailModel!: NgModel;
  @ViewChild('password') passwordModel!: NgModel;

  public loginForm: { email: string; password: string } = {
    email: '',
    password: '',
  };

  constructor(private router: Router, private authService: AuthService) {}

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  public handleLogin(event: Event): void {
    event.preventDefault();

    if (!this.emailModel.valid) {
      this.emailModel.control.markAsTouched();
    }

    if (!this.passwordModel.valid) {
      this.passwordModel.control.markAsTouched();
    }

    if (this.emailModel.valid && this.passwordModel.valid) {
      this.authService.login(this.loginForm).subscribe({
        next: () => this.router.navigateByUrl('/app/dashboard'),
        error: (err: any) => {
          this.loginError = err.error?.exception || 'An error occurred';
        }
      });
    }
  }
}
