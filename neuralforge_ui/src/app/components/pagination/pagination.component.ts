import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

/**
 * Pagination Component
 * 
 * This component handles pagination for data lists. It updates the current page 
 * and triggers data fetching accordingly.
 */
@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.scss'
})
export class PaginationComponent {
  /** Service responsible for handling data fetching and pagination */
  @Input() service: any;

  /** Event emitter for custom pagination calls */
  @Output() callCustomPaginationMethod = new EventEmitter();

  /** Flag to determine if a custom method should be called instead of the default one */
  @Input() customCall: boolean = false;

  /**
   * Handles page changes.
   * @param pPage The selected page number.
   */
  onPage(pPage: number) {
    this.service.search.page = pPage;
    
    if (this.customCall) {
      // Emit event for a custom pagination method
      this.callCustomPaginationMethod.emit();
    } else {
      // Use the default service method to fetch data
      this.service.getAll();
    }
  }
}