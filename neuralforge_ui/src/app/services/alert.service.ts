import { inject, Injectable } from "@angular/core";
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from "@angular/material/snack-bar";

/**
 * AlertService
 *
 * This service provides a centralized way to display alert messages
 * using Angular Material's MatSnackBar.
 */
@Injectable({
  providedIn: 'root',
})
export class AlertService {
  /** Injects the MatSnackBar service */
  private snackBar = inject(MatSnackBar);

  /**
   * Displays an alert message
   * 
   * @param type - Type of the alert (e.g., 'success', 'error')
   * @param message - The message to display
   * @param horizontalPosition - The horizontal position of the snackbar (default: 'center')
   * @param verticalPosition - The vertical position of the snackbar (default: 'top')
   * @param panelClass - Custom CSS classes for styling (default: ['error-snackbar'])
   */
  displayAlert(
    type: string,
    message: string,
    horizontalPosition?: MatSnackBarHorizontalPosition,
    verticalPosition?: MatSnackBarVerticalPosition,
    panelClass?: string[]
  ) {
    // Default message based on type
    let finalMessage =
      !message && type === 'error'
        ? 'An error occurred, please try again later'
        : !message && type === 'success'
        ? 'Success'
        : message;

    this.snackBar.open(finalMessage, 'Cerrar', {
      horizontalPosition: horizontalPosition ?? 'center',
      verticalPosition: verticalPosition ?? 'top',
      panelClass: panelClass ?? ['error-snackbar'],
      duration: 3000,
    });
  }
}