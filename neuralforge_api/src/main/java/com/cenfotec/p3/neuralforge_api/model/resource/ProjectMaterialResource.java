package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resource class representing a material associated with a project.
 * Used for transferring project material data between the client and server.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterialResource {
    /**
     * Unique identifier for the project material.
     */
    private String id;

    /**
     * Type of material, either 'file' or 'hyperlink'.
     */
    private String type;

    /**
     * Original name of the uploaded file.
     */
    private String fileName;

    /**
     * URL path to access the file.
     */
    private String fileUrl;

    /**
     * Description or summary of the project material.
     */
    private String description;

    /**
     * External URL reference when the type is 'hyperlink'.
     */
    private String hyperlink;

    /**
     * ID of the project this material belongs to.
     */
    private String projectId;
    
    /**
     * Timestamp indicating when the material was created.
     */
    private LocalDateTime createdAt;
}