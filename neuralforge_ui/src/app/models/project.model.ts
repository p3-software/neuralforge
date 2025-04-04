import { ProjectMaterial } from "./project-material.model";

export type ProjectType = "LEARNING" | "TEACHING" | "PROGRAMMED_GOAL";

export interface Project {
  id: string;
  name: string;
  description: string;
  projectType: ProjectType;
  createdAt: Date;
  lastModifiedAt: Date | null;
  notify?: boolean;
  materials: ProjectMaterial[];
}
