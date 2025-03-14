package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.UserValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserValidationRepository extends JpaRepository<UserValidationEntity, String> {
    @Query("SELECT uv FROM UserValidationEntity uv " +
            "WHERE uv.user.email = :email AND uv.status = false " +
            "ORDER BY uv.requestedAt DESC LIMIT 1")
    Optional<UserValidationEntity> findLatestPendingValidationByEmail(@Param("email") String email);
}
