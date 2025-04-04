import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { distinctUntilChanged, map, tap } from "rxjs/operators";
import { ProjectMaterial } from "../models/project-material.model";
import { BaseService } from "./base-service";

export interface MaterialUpdate {
  projectId: string;
  type: "add" | "delete";
  material: ProjectMaterial;
}

@Injectable({
  providedIn: "root",
})
export class ProjectMaterialService extends BaseService<ProjectMaterial> {
  protected override source = "api/neuralforge/v1/project-materials";
  private materialUpdatesSubject = new BehaviorSubject<MaterialUpdate | null>(
    null
  );
  private materialsByProjectSubject = new BehaviorSubject<
    Map<string, ProjectMaterial[]>
  >(new Map());

  materialUpdates$ = this.materialUpdatesSubject.asObservable();
  materialsByProject$ = this.materialsByProjectSubject.asObservable();

  getProjectMaterials(projectId: string): Observable<ProjectMaterial[]> {
    return this.http
      .get<ProjectMaterial[]>(`${this.source}/project/${projectId}`)
      .pipe(
        tap((materials) => this.updateMaterialsCache(projectId, materials))
      );
  }

  uploadMaterial(formData: FormData): Observable<ProjectMaterial> {
    return this.http
      .post<ProjectMaterial>(`${this.source}/upload`, formData)
      .pipe(
        tap((material) => {
          const currentMaterials =
            this.materialsByProjectSubject.value.get(material.projectId) || [];
          this.updateMaterialsCache(material.projectId, [
            ...currentMaterials,
            material,
          ]);
          this.materialUpdatesSubject.next({
            projectId: material.projectId,
            type: "add",
            material,
          });
        })
      );
  }

  deleteMaterial(materialId: string): Observable<void> {
    return this.http.delete<void>(`${this.source}/${materialId}`).pipe(
      tap(() => {
        const currentMap = this.materialsByProjectSubject.value;
        for (const [projectId, materials] of currentMap.entries()) {
          const material = materials.find((m) => m.id === materialId);
          if (material) {
            const updatedMaterials = materials.filter(
              (m) => m.id !== materialId
            );
            this.updateMaterialsCache(projectId, updatedMaterials);
            this.materialUpdatesSubject.next({
              projectId,
              type: "delete",
              material,
            });
            break;
          }
        }
      })
    );
  }

  downloadMaterialFile(materialId: string): Observable<Blob> {
    return this.http.get(`${this.source}/download/${materialId}`, {
      responseType: "blob",
    });
  }

  notifyMaterialUpdate(update: MaterialUpdate): void {
    if (update && update.projectId && update.type && update.material) {
      const currentMap = this.materialsByProjectSubject.value;
      const projectMaterials = currentMap.get(update.projectId) || [];

      if (update.type === "add") {
        const existingIdx = projectMaterials.findIndex(
          (m) => m.id === update.material.id
        );
        if (existingIdx === -1) {
          this.updateMaterialsCache(update.projectId, [
            ...projectMaterials,
            update.material,
          ]);
        }
      } else if (update.type === "delete") {
        const existingIdx = projectMaterials.findIndex(
          (m) => m.id === update.material.id
        );
        if (existingIdx !== -1) {
          const updatedMaterials = projectMaterials.filter(
            (m) => m.id !== update.material.id
          );
          this.updateMaterialsCache(update.projectId, updatedMaterials);
        }
      }

      this.materialUpdatesSubject.next(update);
    }
  }

  private updateMaterialsCache(
    projectId: string,
    materials: ProjectMaterial[]
  ): void {
    const currentMap = this.materialsByProjectSubject.value;
    const newMap = new Map(currentMap);
    newMap.set(projectId, materials);
    this.materialsByProjectSubject.next(newMap);
  }

  getMaterialsForProject(projectId: string): Observable<ProjectMaterial[]> {
    const currentMaterials =
      this.materialsByProjectSubject.value.get(projectId);

    if (!currentMaterials || currentMaterials.length === 0) {
      this.getProjectMaterials(projectId).subscribe();
    }

    return this.materialsByProject$.pipe(
      map((materialsMap) => materialsMap.get(projectId) || []),
      distinctUntilChanged(
        (prev, curr) => JSON.stringify(prev) === JSON.stringify(curr)
      )
    );
  }
}
