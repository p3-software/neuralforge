package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.model.entity.ProjectEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.ProjectMaterialEntity;
import com.cenfotec.p3.neuralforge_api.model.mapper.ProjectMaterialMapper;
import com.cenfotec.p3.neuralforge_api.model.resource.ProjectMaterialResource;
import com.cenfotec.p3.neuralforge_api.repository.ProjectMaterialRepository;
import com.cenfotec.p3.neuralforge_api.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing project materials.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProjectMaterialService {

    private final ProjectMaterialRepository materialRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMaterialMapper materialMapper;
    private final String uploadDir = "uploads/materials";

    /**
     * Retrieves all project materials for a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of project material resources.
     */
    @Transactional(readOnly = true)
    public List<ProjectMaterialResource> getProjectMaterials(String projectId) {
        return materialRepository.findByProjectId(projectId)
                .stream()
                .map(materialMapper::mapToResource)
                .collect(Collectors.toList());
    }

    /**
     * Uploads a new project material.
     *
     * @param file The uploaded file.
     * @param type The type of the material.
     * @param description The description of the material.
     * @param hyperlink The hyperlink of the material.
     * @param projectId The ID of the project.
     * @return The created project material resource.
     */
    @Transactional
    public ProjectMaterialResource uploadMaterial(MultipartFile file, String type, String description, String hyperlink, String projectId) {
        if (type == null || (!type.equals("file") && !type.equals("hyperlink"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid material type. Must be 'file' or 'hyperlink'");
        }
        
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + projectId));

        ProjectMaterialEntity material = ProjectMaterialEntity.builder()
                .type(type)
                .description(description)
                .project(project)
                .createdAt(LocalDateTime.now())
                .build();

        if ("file".equals(type)) {
            // Validate file for file type
            if (file == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is null but required for type 'file'");
            }
            
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty but required for type 'file'");
            }
            
            try {
                byte[] bytes = file.getBytes();
                if (bytes.length == 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File content is empty");
                }
            } catch (IOException e) {
                System.err.println("Error reading file content: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot read file content");
            }
            
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file name");
            }
            
            String fileExtension = "";
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            
            try {
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
                Files.createDirectories(uploadPath);
                System.out.println("Upload path: " + uploadPath.toString());

                Path filePath = uploadPath.resolve(uniqueFileName);
                System.out.println("File path: " + filePath.toString());
                Files.copy(file.getInputStream(), filePath);
                System.out.println("File saved successfully");

                material.setFileName(originalFileName);
                material.setFileUrl("/api/neuralforge/v1/project-materials/files/" + uniqueFileName);
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file");
            }
        } else if ("hyperlink".equals(type)) {
            // Validate hyperlink for hyperlink type
            if (hyperlink == null || hyperlink.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hyperlink is required for type 'hyperlink'");
            }
            material.setHyperlink(hyperlink);
        }

        ProjectMaterialEntity savedMaterial = materialRepository.save(material);
        return materialMapper.mapToResource(savedMaterial);
    }

    /**
     * Deletes a project material by its ID.
     *
     * @param materialId The ID of the project material to delete.
     */
    @Transactional
    public void deleteMaterial(String materialId) {
        ProjectMaterialEntity material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found with id: " + materialId));

        if ("file".equals(material.getType()) && material.getFileUrl() != null) {
            try {
                String fileName = material.getFileUrl().substring(material.getFileUrl().lastIndexOf("/") + 1);
                Path filePath = Paths.get(uploadDir, fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log the error but continue with material deletion
            }
        }

        materialRepository.delete(material);
    }

    @Transactional
    public ProjectMaterialResource createProjectMaterial(ProjectMaterialResource material) {
        ProjectEntity project = projectRepository.findById(material.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        
        ProjectMaterialEntity entity = materialMapper.mapToEntity(material);
        ProjectMaterialEntity savedEntity = materialRepository.save(entity);
        return materialMapper.mapToResource(savedEntity);
    }

    @Transactional
    public ProjectMaterialResource updateProjectMaterial(String id, ProjectMaterialResource material) {
        ProjectMaterialEntity existingMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found"));
        
        ProjectEntity project = projectRepository.findById(material.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        
        ProjectMaterialEntity updatedEntity = materialMapper.mapToEntity(material);
        updatedEntity.setId(existingMaterial.getId());
        
        ProjectMaterialEntity savedEntity = materialRepository.save(updatedEntity);
        return materialMapper.mapToResource(savedEntity);
    }

    @Transactional(readOnly = true)
    public ProjectMaterialResource getProjectMaterial(String id) {
        return materialRepository.findById(id)
                .map(materialMapper::mapToResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found"));
    }

    @Transactional(readOnly = true)
    public List<ProjectMaterialResource> getAllProjectMaterials() {
        return materialRepository.findAll()
                .stream()
                .map(materialMapper::mapToResource)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProjectMaterial(String id) {
        if (!materialRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found");
        }
        materialRepository.deleteById(id);
    }
}