package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.model.enums.ValidationTypeEnum;
import com.cenfotec.p3.neuralforge_api.model.mapper.ValidationTypeMapper;
import com.cenfotec.p3.neuralforge_api.model.resource.ValidationTypeResource;
import com.cenfotec.p3.neuralforge_api.repository.ValidationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidationTypeService {

    @Autowired
    protected ValidationTypeRepository validationTypeRepository;

    protected final ValidationTypeMapper validationTypeMapper = new ValidationTypeMapper();

    public ValidationTypeResource getValidationTypeByEnum(ValidationTypeEnum type) {
        return validationTypeMapper.mapToResource(
                validationTypeRepository
                        .findByType(type)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validation type not found"))
        );
    }
}
