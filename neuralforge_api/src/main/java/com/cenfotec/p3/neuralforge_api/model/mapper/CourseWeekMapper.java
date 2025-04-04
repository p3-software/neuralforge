package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.CourseWeekEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.TeachingProjectEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.CourseWeekResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between CourseWeekEntity and CourseWeekResource.
 * Ensures consistent data transformation between the database entity and the API resource.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Component
public class CourseWeekMapper {

    @Autowired
    private ClassSessionMapper classSessionMapper;

    /**
     * Converts a CourseWeekEntity into a CourseWeekResource.
     *
     * @param entity The CourseWeekEntity to be mapped.
     * @return A CourseWeekResource containing the mapped data.
     */
    public CourseWeekResource mapToResource(CourseWeekEntity entity) {
        if (entity == null) {
            return null;
        }

        return CourseWeekResource.builder()
                .id(entity.getId())
                .weekNumber(entity.getWeekNumber())
                .classSessions(entity.getClassSessions() != null ? 
                        entity.getClassSessions().stream()
                                .map(classSessionMapper::mapToResource)
                                .collect(Collectors.toList()) : 
                        Collections.emptyList())
                .build();
    }

    /**
     * Converts a CourseWeekResource into a CourseWeekEntity.
     *
     * @param resource The CourseWeekResource to be mapped.
     * @return A CourseWeekEntity containing the mapped data.
     */
    public CourseWeekEntity mapToEntity(CourseWeekResource resource) {
        if (resource == null) {
            return null;
        }

        return mapToEntity(resource, null);
    }
    
    /**
     * Converts a CourseWeekResource into a CourseWeekEntity with a specified parent project.
     *
     * @param resource The CourseWeekResource to be mapped.
     * @param teachingProject The parent TeachingProjectEntity to associate with this week.
     * @return A CourseWeekEntity containing the mapped data.
     */
    public CourseWeekEntity mapToEntity(CourseWeekResource resource, TeachingProjectEntity teachingProject) {
        if (resource == null) {
            return null;
        }

        CourseWeekEntity entity = CourseWeekEntity.builder()
                .id(resource.getId())
                .weekNumber(resource.getWeekNumber())
                .teachingProject(teachingProject)
                .classSessions(new ArrayList<>())
                .build();
                
        if (resource.getClassSessions() != null) {
            entity.setClassSessions(
                resource.getClassSessions().stream()
                    .map(sessionResource -> classSessionMapper.mapToEntity(sessionResource, entity))
                    .collect(Collectors.toList())
            );
        }
        
        return entity;
    }
} 