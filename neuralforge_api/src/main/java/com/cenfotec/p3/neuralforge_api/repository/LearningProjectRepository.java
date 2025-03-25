package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.LearningProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link LearningProjectEntity} persistence.
 * Extends {@link JpaRepository} to provide CRUD operations on learning project entities.
 *
 * This repository provides specialized methods for retrieving learning projects.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Repository
public interface LearningProjectRepository extends JpaRepository<LearningProjectEntity, String> {

}