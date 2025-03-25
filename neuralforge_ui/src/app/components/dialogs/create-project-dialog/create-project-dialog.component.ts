import { CommonModule } from "@angular/common";
import { Component, inject } from "@angular/core";
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { LearningProjectService } from "../../../services/learning-project.service";

@Component({
  selector: "app-create-project-dialog",
  standalone: true,
  templateUrl: "./create-project-dialog.component.html",
  styleUrls: ["./create-project-dialog.component.scss"],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
  ],
})
export class CreateProjectDialogComponent {
  private learningProjectService = inject(LearningProjectService);

  createProjectForm = new FormGroup({
    name: new FormControl("", [Validators.required]),
    description: new FormControl(""),
  });

  constructor(private dialogRef: MatDialogRef<CreateProjectDialogComponent>) {}

  onSubmit() {
    if (this.createProjectForm.valid) {
      const project = {
        name: this.createProjectForm.value.name,
        description: this.createProjectForm.value.description || null,
      };

      this.learningProjectService.add(project).subscribe({
        next: (response: any) => {
          console.log(response);
        },
        error: (error: any) => {
          console.error(error);
        },
      });

      this.dialogRef.close(this.createProjectForm.value);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
