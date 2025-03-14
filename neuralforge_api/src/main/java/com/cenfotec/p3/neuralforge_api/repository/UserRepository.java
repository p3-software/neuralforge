package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.UserRoleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} persistence.
 * Extends {@link JpaRepository} to provide CRUD operations on the user entity.
 *
 * @author Jareth Mena
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to search for.
     * @return An {@link Optional} containing the user if found, or empty otherwise.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email The email to check for existence.
     * @return {@code true} if a user with the email exists, {@code false} otherwise.
     */
    boolean existsByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
            "u.name = CASE WHEN :name IS NOT NULL AND :name <> '' THEN :name ELSE u.name END, " +
            "u.lastName = CASE WHEN :lastName IS NOT NULL AND :lastName <> '' THEN :lastName ELSE u.lastName END, " +
            "u.password = CASE WHEN :password IS NOT NULL AND :password <> '' THEN :password ELSE u.password END, " +
            "u.verified = CASE WHEN :verified IS NOT NULL AND :verified = true THEN true ELSE u.verified END, " +
//            "u.role = CASE WHEN :role IS NOT NULL THEN :role ELSE u.role END, " +
            "u.status = CASE WHEN :status IS NOT NULL THEN :status ELSE u.status END " +
            "WHERE u.email = :email")
    void updateUser(
            @Param("email") String email,
            @Param("name") String name,
            @Param("lastName") String lastName,
            @Param("password") String password,
            @Param("verified") Boolean verified,
//            @Param("role") UserRoleEntity role,
            @Param("status") Boolean status
    );
}
