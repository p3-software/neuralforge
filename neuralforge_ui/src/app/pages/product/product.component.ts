import { ProductFormComponent } from '../../components/product/product-form/product-form.component';
import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { LoaderComponent } from '../../components/loader/loader.component';
import { ProductService } from '../../services/product.service';
import { PaginationComponent } from '../../components/pagination/pagination.component';
import { ProductComponent } from '../../components/product/product/product.component';
import { ModalComponent } from '../../components/modal/modal.component';
import { ModalService } from '../../services/modal.service';
import { FormBuilder, Validators } from '@angular/forms';
import { IProduct } from '../../interfaces';

@Component({
  standalone: true,
  selector: 'app-product-page',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss'],
  imports: [
    CommonModule,
    LoaderComponent,
    PaginationComponent,
    ProductComponent,
    ModalComponent,
    ProductFormComponent
  ]
})
export class ProductPageComponent {
  public productService: ProductService = inject(ProductService);
  public modalService: ModalService = inject(ModalService);
  public fb: FormBuilder = inject(FormBuilder);
  @ViewChild('addProductModal') public addProductModal: any;
  public title: string = 'Product';
  public productForm = this.fb.group({
    id: [''],
    name: ['', Validators.required],
    description: [''],
    price: [0, Validators.required],
    stockQuantity: [0, Validators.required],
    categoryId: [0, Validators.required]
  });

  constructor() {
    this.productService.getAll();
  }

  saveProduct(item: IProduct) {
    this.productService.save(item);
    this.modalService.closeAll();
  }

  updateProduct(item: IProduct) {
    this.productService.update(item);
    this.modalService.closeAll();
  }

  callEdition(item: IProduct) {
    this.productForm.controls['id'].setValue(item.id ? JSON.stringify(item.id) : '');
    this.productForm.controls['name'].setValue(item.name ? item.name : '');
    this.productForm.controls['description'].setValue(item.description ? item.description : '');
    this.productForm.controls['price'].setValue(item.price ? item.price : 0);
    this.productForm.controls['stockQuantity'].setValue(item.stockQuantity ? item.stockQuantity : 0);
    this.productForm.controls['categoryId'].setValue(item.categoryId ? item.categoryId : 0);
    this.modalService.displayModal('md', this.addProductModal);
  }

  deleteProduct(item: IProduct) {
    this.productService.delete(item);
  }
}