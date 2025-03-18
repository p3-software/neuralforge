package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.exception.customTypes.NeuralForgeEmailException;
import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.PasswordResetRequestResource;
import com.cenfotec.p3.neuralforge_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // Agregamos JwtService para validar el token

    /**
     * Solicita el restablecimiento de la contraseña.
     *
     * @param request Contiene el correo electrónico del usuario que solicita el restablecimiento.
     */
    public void requestPasswordReset(PasswordResetRequestResource request) throws NeuralForgeEmailException {
        Optional<UserEntity> userOpt = userRepository.findByEmail(request.getEmail());

        if (!userOpt.isPresent()) {
            throw new RuntimeException("El correo no está registrado en el sistema.");
        }

        UserEntity user = userOpt.get();
        String token = jwtService.generatePasswordResetToken(user.getId()); // Genera el token
        emailService.sendPasswordResetEmail(user, token);
    }

    /**
     * Restablece la contraseña usando el token proporcionado y la nueva contraseña.
     *
     * @param token      El token de restablecimiento de contraseña.
     * @param newPassword La nueva contraseña del usuario.
     */
    public void resetPassword(String token, String newPassword) {

        String userId;
        try {
            userId = jwtService.extractClaim(token, claims -> claims.get("userId", String.class));
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado.");
        }

        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        UserEntity user = userOpt.get();
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
}