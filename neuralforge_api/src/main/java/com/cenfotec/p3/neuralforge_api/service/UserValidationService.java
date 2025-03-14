package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.UserValidationEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.ValidationTypeEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.ValidationTypeEnum;
import com.cenfotec.p3.neuralforge_api.model.mapper.UserMapper;
import com.cenfotec.p3.neuralforge_api.model.mapper.UserValidationMapper;
import com.cenfotec.p3.neuralforge_api.model.mapper.ValidationTypeMapper;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserValidationInputResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserValidationResource;
import com.cenfotec.p3.neuralforge_api.model.resource.ValidationTypeResource;
import com.cenfotec.p3.neuralforge_api.repository.UserValidationRepository;
import com.cenfotec.p3.neuralforge_api.util.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserValidationService {
    @Autowired
    protected UserValidationRepository userValidationRepository;

    @Autowired
    protected ValidationTypeService validationTypeService;

    @Autowired
    protected GenerationUtil generationUtil;

    protected final UserValidationMapper userValidationMapper = new UserValidationMapper();
    protected final ValidationTypeMapper validationTypeMapper = new ValidationTypeMapper();

    public UserValidationResource createUserValidation(UserEntity user){
        int verificationCode = generationUtil.generateRandomVerificationCode();

        ValidationTypeResource verifyValidationType = validationTypeService.getValidationTypeByEnum(ValidationTypeEnum.VERIFY);

        ValidationTypeEntity validationType = validationTypeMapper.mapToEntity(verifyValidationType);

        UserValidationEntity userValidation = userValidationMapper.mapToEntity(user, validationType, verificationCode);

        return userValidationMapper.mapToResource(userValidationRepository.save(userValidation));
    }

    public void validateInputCode(UserValidationInputResource validationInput) {
        UserValidationEntity latestPendingValidation = getLatestPendingValidation(validationInput.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no pending validations."));

        if (latestPendingValidation.getVerificationCode() != validationInput.getVerificationCode()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation code does not match.");
        }

        latestPendingValidation.setStatus(Boolean.TRUE);

        userValidationRepository.save(latestPendingValidation);
    }

    private Optional<UserValidationEntity> getLatestPendingValidation(String email){
        return userValidationRepository.findLatestPendingValidationByEmail(email);
    }

}
