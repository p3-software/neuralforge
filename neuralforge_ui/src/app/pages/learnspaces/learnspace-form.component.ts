import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LearnspaceService } from '../../services/learnspace.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-learnspace-form',
  templateUrl: './learnspace-form.component.html',
  styleUrls: ['./learnspace-form.component.scss'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class LearnspaceFormComponent {
  learnspaceForm = this.fb.group({
    nombre: ['', [Validators.required, Validators.maxLength(100)]],
    descripcion: ['', [Validators.maxLength(500)]],
    visibilidad: ['Público', [Validators.required]]
  });

  constructor(private fb: FormBuilder, private learnspaceService: LearnspaceService, private router: Router) {}

  onSubmit() {
    if (this.learnspaceForm.invalid) return;

    this.learnspaceService.create(this.learnspaceForm.value).subscribe({
      next: () => this.router.navigate(['/learnspaces']),
      error: () => alert('Error al crear el Learnspace. ¿Estás autenticado?')
    });
  }
}
