import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../../../services/auth.service";

// Importing necessary Angular Material Modules
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { IExceptionResponse } from "../../../interfaces";

@Component({
  selector: "app-password-reset",
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
  ],
  templateUrl: "./reset-password.component.html",
  styleUrls: ["./reset-password.component.scss"],
})
export class PasswordResetComponent implements OnInit {
  token: string | null = null;
  resetForm: FormGroup;
  validationErrors: string[] = [];
  successMessage: string = "";

  // Password visibility toggler
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    this.resetForm = this.fb.group(
      {
        newPassword: ["", [Validators.required, Validators.minLength(8)]],
        confirmPassword: ["", [Validators.required]],
      },
      {
        validators: this.passwordMatchValidator,
      }
    );
  }

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.token = params["token"] || null;
      if (this.token == null) this.router.navigate(["/login"]);
    });
  }

  // Custom validator to check if passwords match
  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get("newPassword")?.value;
    const confirmPassword = formGroup.get("confirmPassword")?.value;

    if (password !== confirmPassword) {
      formGroup.get("confirmPassword")?.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    } else {
      return null;
    }
  }

  // Helper methods for form validation
  get newPasswordControl() {
    return this.resetForm.get("newPassword");
  }
  get confirmPasswordControl() {
    return this.resetForm.get("confirmPassword");
  }

  togglePasswordVisibility(field: "new" | "confirm") {
    if (field === "new") {
      this.showNewPassword = !this.showNewPassword;
    } else {
      this.showConfirmPassword = !this.showConfirmPassword;
    }
  }

  submitForm() {
    this.validationErrors = [];

    // Mark all fields as touched to trigger validation messages
    this.resetForm.markAllAsTouched();

    if (!this.token) {
      this.validationErrors.push("Invalid request. No token provided.");
      return;
    }

    if (this.resetForm.invalid) {
      return;
    }

    const newPassword = this.resetForm.get("newPassword")?.value;

    this.authService.resetPassword(this.token, newPassword).subscribe({
      next: (response) => {
        console.log(response);
        this.successMessage =
          "Password successfully reset. Redirecting to login...";
        setTimeout(() => this.router.navigate(["/login"]), 3000);
      },
      error: (err: IExceptionResponse) => {
        console.log(err);

        this.validationErrors = Array.isArray(err.error.exception)
          ? err.error.exception
          : [err.error.exception];
      },
    });
  }
}
