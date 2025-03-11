package com.cenfotec.p3.neuralforge_api.model.seeder;


import com.cenfotec.p3.neuralforge_api.model.entity.UserRoleEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.UserRoleEnum;
import com.cenfotec.p3.neuralforge_api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        UserRoleEnum[] roleNames = new UserRoleEnum[] {
                UserRoleEnum.ROLE_STUDENT,
                UserRoleEnum.ROLE_TEACHER,
                UserRoleEnum.ROLE_ADMINISTRATOR
        };

        Map<UserRoleEnum, String> roleDescriptionMap = Map.of(
                UserRoleEnum.ROLE_STUDENT, "Default role.",
                UserRoleEnum.ROLE_TEACHER, "Can create teaching projects. Provided by an administrator.",
                UserRoleEnum.ROLE_ADMINISTRATOR, "Has all permissions."
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<UserRoleEntity> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                UserRoleEntity roleToCreate = new UserRoleEntity();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
