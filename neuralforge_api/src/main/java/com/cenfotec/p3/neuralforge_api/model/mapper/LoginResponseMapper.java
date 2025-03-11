package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.resource.AuthenticationResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;

public class LoginResponseMapper {
    public AuthenticationResource mapToResource(UserResource user, String token, Long expirationTime){
        return AuthenticationResource.builder()
                .authUser(user)
                .token(token)
                .expiresIn(expirationTime)
                .build();
    }
}
