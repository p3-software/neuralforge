import { CommonModule } from "@angular/common";
import { Component, Inject, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { ISelectedDays, ITeachingProject } from "../../../interfaces";
import { AlertService } from "../../../services/alert.service";
import { TeachingProjectService } from "../../../services/teaching-project.service";
import {
  ProjectFormComponent,
  ProjectFormData,
} from "../../project-form/project-form.component";
import { WeekdayPickerComponent } from "../../weekday-picker/weekday-picker.component";

export interface EditTeachingProjectData extends ProjectFormData {
  selectedDays: ISelectedDays;
  dailyHours: number;
  startDate: Date;
  endDate: Date;
}

export interface EditTeachingProjectDialogData {
  project: ITeachingProject;
}

@Component({
  selector: "app-edit-teaching-project-dialog",
  standalone: true,
  templateUrl: "./edit-teaching-project-dialog.component.html",
  styleUrls: ["./edit-teaching-project-dialog.component.scss"],
  imports: [
    CommonModule,
    MatDialogModule,
    ProjectFormComponent,
    WeekdayPickerComponent,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
})
export class EditTeachingProjectDialogComponent implements OnInit {
  teachingProject!: EditTeachingProjectData;
  project!: ITeachingProject;

  constructor(
    private dialogRef: MatDialogRef<EditTeachingProjectDialogComponent>,
    private teachingProjectService: TeachingProjectService,
    private alertService: AlertService,
    @Inject(MAT_DIALOG_DATA) public data: EditTeachingProjectDialogData
  ) {}

  ngOnInit(): void {
    // Extract the project from the dialog data
    this.project = this.data.project;

    // Create a copy of the project data to avoid modifying the original
    this.teachingProject = {
      name: this.project.name,
      description: this.project.description || "",
      selectedDays: { ...this.project.selectedDays },
      dailyHours: this.project.dailyHours,
      startDate: this.project.startDate
        ? new Date(this.project.startDate)
        : new Date(this.project.createdAt),
      endDate: this.project.endDate
        ? new Date(this.project.endDate)
        : new Date(
            new Date(this.project.createdAt).setDate(
              new Date(this.project.createdAt).getDate() +
                this.project.weeksCount * 7
            )
          ),
    };
  }

  isFormValid(): boolean {
    return this.getSelectedDaysCount() > 0;
  }

  onSubmit(projectData: ProjectFormData): void {
    if (!this.isFormValid()) {
      this.alertService.displayAlert(
        "error",
        "Please select at least one day of the week",
        "center",
        "top",
        ["error-snackbar"]
      );
      return;
    }

    const weeksCount = this.getWeeksCount();
    const hoursPerClass = 1;

    const updatedProject: ITeachingProject = {
      ...this.project,
      name: projectData.name,
      description: projectData.description,
      selectedDays: this.teachingProject.selectedDays,
      dailyHours: this.teachingProject.dailyHours,
      weeksCount: weeksCount,
      hoursPerClass: hoursPerClass,
      startDate: this.teachingProject.startDate,
      endDate: this.teachingProject.endDate,
      lastModifiedAt: new Date(),
    };

    this.teachingProjectService.update(updatedProject).subscribe({
      next: (response) => {
        this.alertService.displayAlert(
          "success",
          "Teaching project updated successfully",
          "center",
          "top",
          ["success-snackbar"]
        );

        this.dialogRef.close(response);
      },
      error: (error) => {
        this.alertService.displayAlert(
          "error",
          error.error?.exception || "Failed to update teaching project",
          "center",
          "top",
          ["error-snackbar"]
        );

        console.error("Error updating project:", error);
      },
    });
  }

  getSelectedDaysCount(): number {
    return Object.values(this.teachingProject.selectedDays).filter((day) => day)
      .length;
  }

  getTotalHours(): number {
    const weeksCount = this.getWeeksCount();
    return (
      this.getSelectedDaysCount() * this.teachingProject.dailyHours * weeksCount
    );
  }

  getWeeksCount(): number {
    const start = new Date(this.teachingProject.startDate);
    const end = new Date(this.teachingProject.endDate);
    const diffTime = Math.abs(end.getTime() - start.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    const diffWeeks = diffDays / 7;
    return Math.round(diffWeeks * 10) / 10; // Round to 1 decimal place
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
