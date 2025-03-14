package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.UserValidationEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.ValidationTypeEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.UserValidationResource;

public class UserValidationMapper {

    protected final UserMapper userMapper = new UserMapper();
    protected final ValidationTypeMapper validationTypeMapper = new ValidationTypeMapper();

    public UserValidationEntity mapToEntity(UserEntity user, ValidationTypeEntity validationType, int verificationCode){
        return  UserValidationEntity.builder()
                .user(user)
                .type(validationType)
                .verificationCode(verificationCode)
                .build();
    }

    public UserValidationResource mapToResource(UserValidationEntity userValidation){
        return  UserValidationResource.builder()
                .user(userMapper.mapToResource(userValidation.getUser()))
                .type(validationTypeMapper.mapToResource(userValidation.getType()))
                .verificationCode(userValidation.getVerificationCode())
                .status(userValidation.getStatus())
                .requestedAt(userValidation.getRequestedAt())
                .build();
    }

}
