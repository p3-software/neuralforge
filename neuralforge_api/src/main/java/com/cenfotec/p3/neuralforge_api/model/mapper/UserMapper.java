package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;

public class UserMapper {
    public UserResource mapToResource(UserEntity user){
        return UserResource.builder()
                .id(user.getId())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .status(user.isStatus())
                .lastName(user.getLastName())
                .name(user.getName())
                .build();
    }

    public UserEntity mapToEntity(UserResource user){
        return UserEntity.builder()
                .id(user.getId())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .status(user.isStatus())
                .lastName(user.getLastName())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
