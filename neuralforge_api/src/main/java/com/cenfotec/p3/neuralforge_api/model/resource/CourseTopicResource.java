package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Resource class representing a topic to be covered in a class session.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTopicResource {

    /**
     * Unique identifier for the topic.
     */
    private String id;
    
    /**
     * Title of the topic.
     */
    private String title;
    
    /**
     * Detailed description of the topic.
     */
    private String description;
    
    /**
     * Order of this topic within the class session.
     */
    private Integer orderIndex;
    
    /**
     * Estimated duration for this topic in minutes.
     */
    private Integer durationMinutes;
    
    /**
     * Flag indicating if this topic has been locked by the teacher to a specific
     * week, day and position.
     */
    @Builder.Default
    private Boolean teacherLocked = false;
    
    /**
     * Source materials for this topic.
     */
    @Builder.Default
    private Set<ProjectMaterialResource> sourceMaterials = new HashSet<>();
    
    /**
     * Optional page number or section references in the source materials.
     */
    @Builder.Default
    private Map<String, String> sourceReferences = new java.util.HashMap<>();
} 