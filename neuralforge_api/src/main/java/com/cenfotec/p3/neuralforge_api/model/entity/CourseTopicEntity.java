package com.cenfotec.p3.neuralforge_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a topic to be covered in a class session.
 * Topics can be extracted from teaching materials or manually added.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Entity
@Table(name = "course_topics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTopicEntity {

    /**
     * Unique identifier for the topic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Class session this topic belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session_id", nullable = false)
    private ClassSessionEntity classSession;

    /**
     * Title of the topic.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of the topic.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Order of this topic within the class session.
     */
    @Column(nullable = false)
    private Integer orderIndex;

    /**
     * Estimated duration for this topic in minutes.
     */
    private Integer durationMinutes;

    /**
     * Source materials for this topic.
     * A topic can be present in multiple materials, and a material can contain multiple topics.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "course_topic_materials",
        joinColumns = @JoinColumn(name = "topic_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    @Builder.Default
    private Set<ProjectMaterialEntity> sourceMaterials = new HashSet<>();

    /**
     * Optional page number or section references in the source materials.
     * Maps material IDs to their page/section references.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_topic_source_references", 
                   joinColumns = @JoinColumn(name = "topic_id"))
    @MapKeyColumn(name = "material_id")
    @Column(name = "source_reference")
    @Builder.Default
    private java.util.Map<String, String> sourceReferences = new java.util.HashMap<>();

    /**
     * Flag indicating if this topic has been locked by the teacher to a specific
     * week, day and position. Locked topics won't be moved automatically during
     * schedule reorganization.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean teacherLocked = false;
} 