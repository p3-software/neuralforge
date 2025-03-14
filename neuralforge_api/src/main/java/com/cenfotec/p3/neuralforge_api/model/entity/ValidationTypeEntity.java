package com.cenfotec.p3.neuralforge_api.model.entity;

import com.cenfotec.p3.neuralforge_api.model.enums.ValidationTypeEnum;
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

@Data
@Table(name = "validation_types")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidationTypeEnum type;

    private String description;
}
