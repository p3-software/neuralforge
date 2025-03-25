package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.ProjectEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.ProjectResource;

/**
 * Mapper class responsible for converting between {@link ProjectEntity} and {@link ProjectResource}.
 * Ensures consistent data transformation between the database entity and the API resource
 * for abstract project types.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
public abstract class ProjectMapper<E extends ProjectEntity, R extends ProjectResource> {

    /**
     * Converts a project entity into its corresponding resource.
     *
     * @param entity The project entity to be mapped.
     * @return A resource containing the mapped project data.
     */
    public abstract R mapToResource(E entity);

    /**
     * Converts a project resource into its corresponding entity.
     *
     * @param resource The project resource to be mapped.
     * @return An entity containing the mapped project data.
     */
    public abstract E mapToEntity(R resource);
    
    /**
     * Copies common project fields from entity to resource.
     * 
     * @param entity The source entity
     * @param resource The target resource
     */
    protected void copyEntityToResource(ProjectEntity entity, ProjectResource resource) {
        resource.setId(entity.getId());
        resource.setName(entity.getName());
        resource.setDescription(entity.getDescription());
        resource.setProjectType(entity.getProjectType());
    }
    
    /**
     * Copies common project fields from resource to entity.
     * 
     * @param resource The source resource
     * @param entity The target entity
     */
    protected void copyResourceToEntity(ProjectResource resource, ProjectEntity entity) {
        entity.setId(resource.getId());
        entity.setName(resource.getName());
        entity.setDescription(resource.getDescription());
    }
}