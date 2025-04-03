package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.TeachingProjectEntity;
import com.cenfotec.p3.neuralforge_api.model.enums.ProjectTypeEnum;
import com.cenfotec.p3.neuralforge_api.model.resource.TeachingProjectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between {@link TeachingProjectEntity} 
 * and {@link TeachingProjectResource}.
 * Extends the base {@link ProjectMapper} to provide specific mapping for 
 * teaching-focused projects while maintaining the common project mapping logic.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Component

public class TeachingProjectMapper extends ProjectMapper<TeachingProjectEntity, TeachingProjectResource> {

    @Autowired
    private ProjectMaterialMapper projectMaterialMapper;
    
    @Autowired
    private SelectedDaysMapper selectedDaysMapper;

    /**
     * Converts a {@link TeachingProjectEntity} into a {@link TeachingProjectResource}.
     *
     * @param teachingProject The {@link TeachingProjectEntity} to be mapped.
     * @return A {@link TeachingProjectResource} containing the mapped teaching project data.
     */
    @Override
    public TeachingProjectResource mapToResource(TeachingProjectEntity teachingProject) {
        return TeachingProjectResource.builder()
                .id(teachingProject.getId())
                .creatorUserId(teachingProject.getCreatorUserId())
                .name(teachingProject.getName())
                .description(teachingProject.getDescription())
                .createdAt(teachingProject.getCreatedAt())
                .lastModifiedAt(teachingProject.getLastModifiedAt())
                .projectType(ProjectTypeEnum.TEACHING)
                .materials(teachingProject.getMaterials().stream().map(projectMaterialMapper::mapToResource).toList())
                .selectedDays(selectedDaysMapper.toResource(teachingProject.getSelectedDays()))
                .dailyHours(teachingProject.getDailyHours())
                .weeksCount(teachingProject.getWeeksCount())
                .startDate(teachingProject.getStartDate())
                .endDate(teachingProject.getEndDate())
                .build();
    }

    /**
     * Converts a {@link TeachingProjectResource} into a {@link TeachingProjectEntity}.
     *
     * @param teachingProject The {@link TeachingProjectResource} to be mapped.
     * @return A {@link TeachingProjectEntity} containing the mapped teaching project data.
     */
    @Override
    public TeachingProjectEntity mapToEntity(TeachingProjectResource teachingProject) {
        return TeachingProjectEntity.builder()
                .id(teachingProject.getId())
                .creatorUserId(teachingProject.getCreatorUserId())
                .name(teachingProject.getName())
                .description(teachingProject.getDescription())
                .createdAt(teachingProject.getCreatedAt())
                .lastModifiedAt(teachingProject.getLastModifiedAt())
                .materials(teachingProject.getMaterials().stream().map(projectMaterialMapper::mapToEntity).toList())
                .selectedDays(selectedDaysMapper.toEntity(teachingProject.getSelectedDays()))
                .dailyHours(teachingProject.getDailyHours())
                .weeksCount(teachingProject.getWeeksCount())
                .startDate(teachingProject.getStartDate())
                .endDate(teachingProject.getEndDate())
                .build();
    }
} 