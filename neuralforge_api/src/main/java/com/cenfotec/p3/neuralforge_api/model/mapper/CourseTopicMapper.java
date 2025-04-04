package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.ClassSessionEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.CourseTopicEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.CourseTopicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between CourseTopicEntity and CourseTopicResource.
 * Ensures consistent data transformation between the database entity and the API resource.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Component
public class CourseTopicMapper {

    @Autowired
    private ProjectMaterialMapper projectMaterialMapper;

    /**
     * Converts a CourseTopicEntity into a CourseTopicResource.
     *
     * @param entity The CourseTopicEntity to be mapped.
     * @return A CourseTopicResource containing the mapped data.
     */
    public CourseTopicResource mapToResource(CourseTopicEntity entity) {
        if (entity == null) {
            return null;
        }

        return CourseTopicResource.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .orderIndex(entity.getOrderIndex())
                .durationMinutes(entity.getDurationMinutes())
                .teacherLocked(entity.getTeacherLocked())
                .sourceMaterials(entity.getSourceMaterials() != null ?
                        entity.getSourceMaterials().stream()
                                .map(projectMaterialMapper::mapToResource)
                                .collect(Collectors.toSet()) :
                        Collections.emptySet())
                .sourceReferences(entity.getSourceReferences())
                .build();
    }

    /**
     * Converts a CourseTopicResource into a CourseTopicEntity.
     *
     * @param resource The CourseTopicResource to be mapped.
     * @return A CourseTopicEntity containing the mapped data.
     */
    public CourseTopicEntity mapToEntity(CourseTopicResource resource) {
        if (resource == null) {
            return null;
        }

        return mapToEntity(resource, null);
    }
    
    /**
     * Converts a CourseTopicResource into a CourseTopicEntity with a specified parent session.
     *
     * @param resource The CourseTopicResource to be mapped.
     * @param classSession The parent ClassSessionEntity to associate with this topic.
     * @return A CourseTopicEntity containing the mapped data.
     */
    public CourseTopicEntity mapToEntity(CourseTopicResource resource, ClassSessionEntity classSession) {
        if (resource == null) {
            return null;
        }

        return CourseTopicEntity.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .description(resource.getDescription())
                .orderIndex(resource.getOrderIndex())
                .durationMinutes(resource.getDurationMinutes())
                .teacherLocked(resource.getTeacherLocked())
                .classSession(classSession)
                .sourceMaterials(resource.getSourceMaterials() != null ?
                        resource.getSourceMaterials().stream()
                                .map(projectMaterialMapper::mapToEntity)
                                .collect(Collectors.toSet()) :
                        new HashSet<>())
                .sourceReferences(resource.getSourceReferences())
                .build();
    }
} 