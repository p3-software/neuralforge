import { inject, Injectable } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

/**
 * Service for managing modal dialogs using ng-bootstrap's modal component.
 * Provides methods to open and close modals centrally.
 */
@Injectable({
  providedIn: 'root',
})
export class ModalService {
  /** Injected instance of NgbModal for handling modal operations. */
  private ngbModalService: NgbModal = inject(NgbModal);

  /**
   * Opens a modal with the specified size and modal component instance.
   * @param size - The size of the modal ('sm', 'lg', 'xl', etc.).
   * @param modalInstance - The modal component instance to be displayed.
   */
  displayModal(size: string, modalInstance: any) {
    const modalRef = this.ngbModalService.open(modalInstance, {
      size: size ? size : 'sm', // Default size is 'sm' if not provided.
      centered: true, // Centers the modal on the screen.
      backdrop: 'static', // Prevents closing the modal when clicking outside.
      keyboard: false // Disables closing the modal with the Escape key.
    });
  }

  /**
   * Closes all currently open modals.
   */
  closeAll() {
    this.ngbModalService.dismissAll();
  }
}