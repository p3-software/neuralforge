import { CommonModule } from '@angular/common';
import { Component, ViewChild, OnInit, AfterViewInit } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";

declare const google: any; // Avoid TypeScript errors for Google API

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
export class LoginComponent implements OnInit, AfterViewInit {

  public showPassword: boolean = false;
  public loginError!: string;

  @ViewChild('email') emailModel!: NgModel;
  @ViewChild('password') passwordModel!: NgModel;

  public loginForm: { email: string; password: string } = {
    email: '',
    password: '',
  };

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    (window as any).handleCredentialResponse = (response: any) => {
      this.handleGoogleLogin(response.credential);
    };
  }

  ngAfterViewInit(): void {
    this.loadGoogleSignIn();
  }

  private loadGoogleSignIn(): void {
    setTimeout(() => {
      if (google && google.accounts) {
        google.accounts.id.initialize({
          client_id: "YOUR_GOOGLE_CLIENT_ID",
          callback: (response: any) => this.handleGoogleLogin(response.credential)
        });

        google.accounts.id.prompt();
      } else {
        console.error("Google API not loaded yet.");
      }
    }, 500);
  }

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  public handleLogin(event: Event): void {
    event.preventDefault();

    if (!this.emailModel.valid) {
      this.emailModel.control.markAsTouched();
    }

      if (!this.emailModel.valid) this.emailModel.control.markAsTouched();
      if (!this.passwordModel.valid) this.passwordModel.control.markAsTouched();

    if (this.emailModel.valid && this.passwordModel.valid) {
      this.authService.login(this.loginForm).subscribe({
        next: () => this.router.navigateByUrl('/app/dashboard'),
        error: (err: any) => {
          this.loginError = err.error?.exception || 'An error occurred';
        }
      });
    }
  }

  private handleGoogleLogin(token: string): void {
    this.authService.sendGoogleTokenToApi(token).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (err: any) => {

        if (err.status === 404) {
          this.loginError = 'This account is not registered. Please create an account first.';
        } else if (err?.error?.exception?.includes('Account verification pending')) {
          this.loginError = 'Your account is not verified. Please check your email for verification.';
        } else {
          this.loginError = err?.error?.message || err?.message || 'An error occurred during authentication.';
        }
      }
    });
  }
}