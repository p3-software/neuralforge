package com.cenfotec.p3.neuralforge_api.controller;

import com.cenfotec.p3.neuralforge_api.model.resource.ProjectMaterialResource;
import com.cenfotec.p3.neuralforge_api.service.ProjectMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * REST controller for managing project materials.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@RestController
@RequestMapping("/project-materials")
public class ProjectMaterialController {

    @Autowired
    private ProjectMaterialService materialService;

    private final String baseUploadDir = "uploads";
    private final String materialsDir = "materials";

    /**
     * Get the full path to the uploads directory.
     * @return The Path to the uploads directory.
     */
    private Path getUploadPath() {
        return Paths.get(baseUploadDir, materialsDir);
    }

    /**
     * Creates a new project material.
     *
     * @param material The project material resource to create.
     * @return The created project material resource.
     */
    @PostMapping
    public ResponseEntity<ProjectMaterialResource> createProjectMaterial(@RequestBody ProjectMaterialResource material) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materialService.createProjectMaterial(material));
    }

    /**
     * Updates an existing project material.
     *
     * @param id The ID of the project material to update.
     * @param material The updated project material resource.
     * @return The updated project material resource.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectMaterialResource> updateProjectMaterial(
            @PathVariable String id,
            @RequestBody ProjectMaterialResource material) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialService.updateProjectMaterial(id, material));
    }

    /**
     * Retrieves a project material by its ID.
     *
     * @param id The ID of the project material to retrieve.
     * @return The project material resource.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectMaterialResource> getProjectMaterial(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialService.getProjectMaterial(id));
    }

    /**
     * Retrieves all project materials.
     *
     * @return A list of project material resources.
     */
    @GetMapping
    public ResponseEntity<List<ProjectMaterialResource>> getAllProjectMaterials() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialService.getAllProjectMaterials());
    }

    /**
     * Deletes a project material by its ID.
     *
     * @param id The ID of the project material to delete.
     * @return A response with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectMaterial(@PathVariable String id) {
        materialService.deleteMaterial(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * Retrieves all project materials for a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of project materials.
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectMaterialResource>> getProjectMaterials(@PathVariable String projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialService.getProjectMaterials(projectId));
    }

    /**
     * Uploads a new material for a project.
     *
     * @param file The file to upload (for file type materials).
     * @param type The type of material (file or hyperlink).
     * @param description The description of the material.
     * @param hyperlink The hyperlink URL (for hyperlink type materials).
     * @param projectId The ID of the project to associate the material with.
     * @return The created project material.
     */
    @PostMapping("/upload")
    public ResponseEntity<ProjectMaterialResource> uploadMaterial(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String type,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String hyperlink,
            @RequestParam String projectId) {
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materialService.uploadMaterial(file, type, description, hyperlink, projectId));
    }

    /**
     * Securely downloads a file by material ID, verifying the user has access to the project.
     * This endpoint is protected and requires authentication.
     *
     * @param materialId The ID of the material to download.
     * @return A response containing the file resource.
     */
    @GetMapping("/download/{materialId}")
    public ResponseEntity<Resource> downloadSecureFile(@PathVariable String materialId) {
        return materialService.downloadMaterialFile(materialId);
    }
} 