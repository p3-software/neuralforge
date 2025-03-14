package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.ValidationTypeEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.ValidationTypeResource;

public class ValidationTypeMapper {

    public ValidationTypeEntity mapToEntity(ValidationTypeResource validationType){
        return  ValidationTypeEntity.builder()
                .type(validationType.getType())
                .description(validationType.getDescription())
                .id(validationType.getId())
                .build();
    }

    public ValidationTypeResource mapToResource(ValidationTypeEntity validationType){
        return  ValidationTypeResource.builder()
                .type(validationType.getType())
                .description(validationType.getDescription())
                .id(validationType.getId())
                .build();
    }
}
