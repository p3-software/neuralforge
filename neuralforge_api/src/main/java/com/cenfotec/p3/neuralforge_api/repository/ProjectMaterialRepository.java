package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.ProjectMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing project materials.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Repository
public interface ProjectMaterialRepository extends JpaRepository<ProjectMaterialEntity, String> {
    /**
     * Finds all project materials associated with a specific project ID.
     *
     * @param projectId The ID of the project to find materials for.
     * @return A list of project materials associated with the specified project ID.
     */
    List<ProjectMaterialEntity> findByProjectId(String projectId);
} 