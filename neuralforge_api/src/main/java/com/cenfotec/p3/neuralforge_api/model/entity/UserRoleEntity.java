package com.cenfotec.p3.neuralforge_api.model.entity;

import com.cenfotec.p3.neuralforge_api.model.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Data
@Table(name = "user_roles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    private String description;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return Collections.singletonList((GrantedAuthority) () -> name.name());
    }
}
