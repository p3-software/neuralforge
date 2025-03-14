package com.cenfotec.p3.neuralforge_api.model.seeder;

import com.cenfotec.p3.neuralforge_api.model.entity.ValidationTypeEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.ValidationTypeEnum;
import com.cenfotec.p3.neuralforge_api.repository.ValidationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class ValidationTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ValidationTypeRepository validationTypeRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadTypes();
    }
    private void loadTypes() {
        ValidationTypeEnum[] validationTypeNames = new ValidationTypeEnum[] {
                ValidationTypeEnum.RECOVER,
                ValidationTypeEnum.VERIFY,
        };

        Map<ValidationTypeEnum, String> validationDescriptionMap = Map.of(
                ValidationTypeEnum.RECOVER, "Validation type used when recovering an account",
                ValidationTypeEnum.VERIFY, "Validation type used when verifying an user's identity"
        );

        Arrays.stream(validationTypeNames).forEach((typeName) -> {
            Optional<ValidationTypeEntity> optionalValidationType = validationTypeRepository.findByType(typeName);

            optionalValidationType.ifPresentOrElse(System.out::println, () -> {
                ValidationTypeEntity validationTypeToCreate = new ValidationTypeEntity();

                validationTypeToCreate.setType(typeName);
                validationTypeToCreate.setDescription(validationDescriptionMap.get(typeName));

                validationTypeRepository.save(validationTypeToCreate);
            });
        });
    }
}
