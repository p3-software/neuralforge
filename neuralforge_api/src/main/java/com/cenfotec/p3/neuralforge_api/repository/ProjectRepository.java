package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing projects.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
} 