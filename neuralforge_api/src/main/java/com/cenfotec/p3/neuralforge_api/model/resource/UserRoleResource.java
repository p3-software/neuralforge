package com.cenfotec.p3.neuralforge_api.model.resource;

import com.cenfotec.p3.neuralforge_api.model.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResource {
    private String id;
    private UserRoleEnum name;
    private String description;
}
