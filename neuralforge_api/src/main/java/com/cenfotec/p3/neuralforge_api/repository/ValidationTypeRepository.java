package com.cenfotec.p3.neuralforge_api.repository;

import com.cenfotec.p3.neuralforge_api.model.entity.ValidationTypeEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.ValidationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationTypeRepository extends JpaRepository<ValidationTypeEntity, String> {

    Optional<ValidationTypeEntity> findByType(ValidationTypeEnum type);

}
