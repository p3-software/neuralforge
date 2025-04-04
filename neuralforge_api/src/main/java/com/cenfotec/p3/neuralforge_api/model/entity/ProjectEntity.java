package com.cenfotec.p3.neuralforge_api.model.entity;

import com.cenfotec.p3.neuralforge_api.model.enums.ProjectTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract entity representing the base class for all projects in the system.
 * Implements inheritance using joined tables strategy with a discriminator column.
 * 
 * This entity serves as the foundation for specialized project types
 * while maintaining common attributes across all project variants.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Entity
@Data
@Table(name = "projects")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "project_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ProjectEntity {
    
    /**
     * Unique identifier for the project.
     * Generated automatically as a UUID string.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * ID of the user who created this project.
     * This field establishes ownership of the project.
     */
    @Column(nullable = false)
    private String creatorUserId;
    
    /**
     * Name of the project.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Detailed description of the project.
     * Limited to 1000 characters to allow for comprehensive project outlines
     * while maintaining database performance.
     */
    @Column(length = 1000)
    private String description;
    
    /**
     * Type of project, stored as an enumerated value.
     * This field is managed by the discriminator column mechanism
     * and should not be directly modified.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", insertable = false, updatable = false)
    private ProjectTypeEnum projectType;

    /**
     * Timestamp representing when the project was created.
     * Automatically set when the entity is persisted.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    /**
     * Timestamp representing when the project was last modified.
     * Updated automatically whenever the entity is changed.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_at", nullable = false)
    private Date lastModifiedAt;

    /**
     * List of materials associated with this project.
     * Establishes a one-to-many relationship with ProjectMaterialEntity.
     * When a project is deleted, all associated materials will also be removed.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMaterialEntity> materials = new ArrayList<>();

    /**
     * Lifecycle callback triggered before this entity is persisted to the database.
     * Sets the creation and last modified timestamps to the current date and time.
     */
    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.lastModifiedAt = now;
    }

    /**
     * Lifecycle callback triggered before this entity is updated in the database.
     * Updates the last modified timestamp to the current date and time.
     */
    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = new Date();
    }
}