package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.exception.customTypes.NeuralForgeEmailException;
import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.UserRoleEnum;
import com.cenfotec.p3.neuralforge_api.model.mapper.UserMapper;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserRoleResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserValidationInputResource;
import com.cenfotec.p3.neuralforge_api.model.resource.UserValidationResource;
import com.cenfotec.p3.neuralforge_api.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing user operations such as creation and retrieval.
 * Handles user role assignment, password encoding, and database interactions.
 *
 * @author Jareth Mena
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleService userRoleService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserValidationService userValidationService;

    @Autowired
    protected EmailService emailService;

    protected final UserMapper userMapper = new UserMapper();

    public UserResource createUser(UserResource inputUser) throws NeuralForgeEmailException {
        if (userRepository.existsByEmail(inputUser.getEmail())) {
            throw new EntityExistsException("A user is already registered with this email address.");
        }

        UserRoleResource basicRole = userRoleService.getRoleByEnum(UserRoleEnum.ROLE_STUDENT);

        inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));

        inputUser.setRole(basicRole);

        UserEntity storedUser = userRepository.save(userMapper.mapToEntity(inputUser));

        UserValidationResource storedUserValidation = userValidationService.createUserValidation(storedUser);

        emailService.sendUserVerificationEmail(storedUser, storedUserValidation.getVerificationCode());

        return userMapper.mapToResource(storedUser);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of {@link UserResource} representing all users.
     */
    public List<UserResource> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToResource)
                .collect(Collectors.toList());
    }

    public UserResource getUserByEmail(String email){
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no user associated to this email."));
        return userMapper.mapToResource(user);
    }

    public UserResource updateUserFullAccess(UserResource inputUser){
        if (!userRepository.existsByEmail(inputUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no user associated to this email.");
        }

        inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));

        UserEntity user = userMapper.mapToEntity(inputUser);

        userRepository.updateUser(
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getPassword(),
                user.getVerified(),
//                user.getRole(),
                user.getStatus()
        );

        return getUserByEmail(inputUser.getEmail());
    }

    public void validateInitialRegister(UserValidationInputResource validationInput){
        UserResource user = getUserByEmail(validationInput.getEmail());
        userValidationService.validateInputCode(validationInput);
        user.setVerified(true);
        updateUserFullAccess(user);
    }
}
