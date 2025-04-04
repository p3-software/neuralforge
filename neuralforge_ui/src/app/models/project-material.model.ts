export interface ProjectMaterial {
  id: string;
  type: "file" | "hyperlink";
  fileName?: string;
  fileUrl?: string;
  description?: string;
  hyperlink?: string;
  projectId: string;
  createdAt: Date;
  lastModifiedAt?: string;
}
