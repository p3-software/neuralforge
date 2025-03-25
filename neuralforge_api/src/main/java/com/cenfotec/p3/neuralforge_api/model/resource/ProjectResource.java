package com.cenfotec.p3.neuralforge_api.model.resource;

import com.cenfotec.p3.neuralforge_api.model.enums.ProjectTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Resource class representing the base data transfer object for all projects.
 * Contains common attributes shared across all project types.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProjectResource {
    
    /**
     * Unique identifier for the project.
     */
    private String id;
    
    /**
     * Name of the project.
     * Must not be blank.
     */
    @NotBlank(message = "Project name is required")
    private String name;
    
    /**
     * Detailed description of the project.
     * Limited to 1000 characters.
     */
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    /**
     * Type of project, represented as an enumerated value.
     */
    private ProjectTypeEnum projectType;
}