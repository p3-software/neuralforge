package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resource class representing a material associated with a project.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterialResource {

    private String id;
    private String type;
    private String fileName;
    private String fileUrl;
    private String description;
    private String hyperlink;
    private String projectId;
    private LocalDateTime createdAt;
}