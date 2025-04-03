package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.ProjectEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.ProjectMaterialEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.ProjectMaterialResource;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between {@link ProjectMaterialEntity} 
 * and {@link ProjectMaterialResource}.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Component
public class ProjectMaterialMapper {

    /**
     * Converts a {@link ProjectMaterialEntity} into a {@link ProjectMaterialResource}.
     *
     * @param entity The {@link ProjectMaterialEntity} to be mapped.
     * @return A {@link ProjectMaterialResource} containing the mapped material data.
     */
    public ProjectMaterialResource mapToResource(ProjectMaterialEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProjectMaterialResource.builder()
                .id(entity.getId())
                .type(entity.getType())
                .fileName(entity.getFileName())
                .fileUrl(entity.getFileUrl())
                .description(entity.getDescription())
                .hyperlink(entity.getHyperlink())
                .projectId(entity.getProject().getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    /**
     * Converts a {@link ProjectMaterialResource} into a {@link ProjectMaterialEntity}.
     *
     * @param resource The {@link ProjectMaterialResource} to be mapped.
     * @return A {@link ProjectMaterialEntity} containing the mapped material data.
     */
    public ProjectMaterialEntity mapToEntity(ProjectMaterialResource resource) {
        if (resource == null) {
            return null;
        }

        return ProjectMaterialEntity.builder()
                .id(resource.getId())
                .type(resource.getType())
                .fileName(resource.getFileName())
                .fileUrl(resource.getFileUrl())
                .description(resource.getDescription())
                .hyperlink(resource.getHyperlink())
                .build();
    }
} 