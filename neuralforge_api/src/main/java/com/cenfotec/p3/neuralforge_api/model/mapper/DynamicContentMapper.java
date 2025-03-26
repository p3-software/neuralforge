package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.DynamicContentEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.DynamicContentResource;

/**
 * Mapper class for converting DynamicContentEntity to DynamicContentResource.
 * Exposes methods to map between the entity and resource representations.
 *
 * @author Fabian Vargas
 * @version 1.0
 */
public class DynamicContentMapper {

    /**
     * Maps a DynamicContentEntity to a DynamicContentResource.
     *
     * @param entity The DynamicContentEntity to be mapped.
     * @return The corresponding DynamicContentResource.
     */
    public DynamicContentResource mapToResource(DynamicContentEntity entity) {
        if (entity == null) {
            return null;
        }

        DynamicContentResource resource = new DynamicContentResource();
        resource.setId(entity.getId());
        resource.setTitle(entity.getTitle());
        resource.setCreationDate(entity.getCreationDate());
        resource.setPath(entity.getPath());
        resource.setEmail(entity.getEmail());
        resource.setType(entity.getType());

        return resource;
    }

    /**
     * Maps a DynamicContentResource to a DynamicContentEntity.
     *
     * @param resource The DynamicContentResource to be mapped.
     * @return The corresponding DynamicContentEntity.
     */
    public DynamicContentEntity mapToEntity(DynamicContentResource resource) {
        if (resource == null) {
            return null;
        }

        DynamicContentEntity entity = new DynamicContentEntity();
        entity.setId(resource.getId());
        entity.setTitle(resource.getTitle());
        entity.setCreationDate(resource.getCreationDate());
        entity.setPath(resource.getPath());
        entity.setEmail(resource.getEmail());
        entity.setType(resource.getType());

        return entity;
    }
}