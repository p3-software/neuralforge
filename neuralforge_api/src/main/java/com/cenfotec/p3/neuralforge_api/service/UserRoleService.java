package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.model.enums.UserRoleEnum;
import com.cenfotec.p3.neuralforge_api.model.mapper.UserRoleMapper;
import com.cenfotec.p3.neuralforge_api.model.resource.UserRoleResource;
import com.cenfotec.p3.neuralforge_api.repository.UserRepository;
import com.cenfotec.p3.neuralforge_api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRoleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper = new UserRoleMapper();

    public UserRoleResource getRoleByEnum(UserRoleEnum role){
        return userRoleMapper.mapToResource(
                userRoleRepository
                        .findByName(role)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Default role not found"))
        );

    }

}
