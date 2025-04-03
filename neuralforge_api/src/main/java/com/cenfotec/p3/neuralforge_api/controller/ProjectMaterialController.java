package com.cenfotec.p3.neuralforge_api.controller;

import com.cenfotec.p3.neuralforge_api.model.resource.ProjectMaterialResource;
import com.cenfotec.p3.neuralforge_api.service.ProjectMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequiredArgsConstructor
public class ProjectMaterialController {

    private final ProjectMaterialService materialService;
    private final String uploadDir = "uploads/materials";

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

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectMaterialResource>> getProjectMaterials(@PathVariable String projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialService.getProjectMaterials(projectId));
    }

    @PostMapping("/upload")
    public ResponseEntity<ProjectMaterialResource> uploadMaterial(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String type,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String hyperlink,
            @RequestParam String projectId) {
        
        // Debug logging
        if (file != null) {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());
        } else {
            System.out.println("No file received");
        }
        System.out.println("Type: " + type);
        System.out.println("ProjectId: " + projectId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materialService.uploadMaterial(file, type, description, hyperlink, projectId));
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 