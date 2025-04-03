import { CommonModule } from "@angular/common";
import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDialog } from "@angular/material/dialog";
import { MatIconModule } from "@angular/material/icon";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ConfirmDialogComponent } from "../../../components/dialogs/confirm-dialog/confirm-dialog.component";
import { ProjectMaterial } from "../../../models/project-material.model";
import { ProjectMaterialService } from "../../../services/project-material.service";
import { UploadMaterialDialogComponent } from "./upload-material-dialog/upload-material-dialog.component";

@Component({
  selector: "app-materials",
  templateUrl: "./materials.component.html",
  styleUrls: ["./materials.component.scss"],
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
})
export class MaterialsComponent implements OnInit, OnChanges {
  @Input() projectId!: string;
  @Input() projectMaterials?: ProjectMaterial[];
  materials: ProjectMaterial[] = [];
  isLoading = false;

  constructor(
    private projectMaterialService: ProjectMaterialService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (!this.projectMaterials) {
      this.loadMaterials();
    } else {
      this.materials = this.projectMaterials;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (
      changes["projectMaterials"] &&
      changes["projectMaterials"].currentValue
    ) {
      this.materials = changes["projectMaterials"].currentValue;
      this.isLoading = false;
    }
  }

  loadMaterials(): void {
    this.isLoading = true;
    this.projectMaterialService.getProjectMaterials(this.projectId).subscribe({
      next: (materials) => {
        this.materials = materials;
        this.isLoading = false;
      },
      error: (error) => {
        this.snackBar.open("Error loading materials", "Close", {
          duration: 3000,
        });
        this.isLoading = false;
      },
    });
  }

  openUploadDialog(): void {
    const dialogRef = this.dialog.open(UploadMaterialDialogComponent, {
      width: "500px",
      data: { projectId: this.projectId },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // If materials were passed in from parent, we need to reload
        if (this.projectMaterials) {
          this.loadMaterials();
        } else {
          this.loadMaterials();
        }
        this.snackBar.open("Material uploaded successfully", "Close", {
          duration: 3000,
        });
      }
    });
  }

  deleteMaterial(materialId: string): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: "400px",
      data: {
        title: "Delete Material",
        message: "Are you sure you want to delete this material?",
        confirmText: "Delete",
        cancelText: "Cancel",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.projectMaterialService.deleteMaterial(materialId).subscribe({
          next: () => {
            this.materials = this.materials.filter((m) => m.id !== materialId);
            this.snackBar.open("Material deleted successfully", "Close", {
              duration: 3000,
            });
          },
          error: (error) => {
            this.snackBar.open("Error deleting material", "Close", {
              duration: 3000,
            });
          },
        });
      }
    });
  }

  getMaterialIcon(type: string): string {
    return type === "file" ? "insert_drive_file" : "link";
  }
}
