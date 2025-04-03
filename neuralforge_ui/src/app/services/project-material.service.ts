import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProjectMaterial } from "../models/project-material.model";
import { BaseService } from "./base-service";

@Injectable({
  providedIn: "root",
})
export class ProjectMaterialService extends BaseService<ProjectMaterial> {
  protected override source = "api/neuralforge/v1/project-materials";

  getProjectMaterials(projectId: string): Observable<ProjectMaterial[]> {
    return this.http.get<ProjectMaterial[]>(
      `${this.source}/project/${projectId}`
    );
  }

  uploadMaterial(formData: FormData): Observable<ProjectMaterial> {
    return this.http.post<ProjectMaterial>(`${this.source}/upload`, formData, {
      reportProgress: true,
      observe: "body",
    });
  }

  deleteMaterial(materialId: string): Observable<void> {
    return this.http.delete<void>(`${this.source}/${materialId}`);
  }

  downloadMaterialFile(materialId: string): Observable<Blob> {
    return this.http.get(`${this.source}/download/${materialId}`, {
      responseType: "blob",
    });
  }
}
