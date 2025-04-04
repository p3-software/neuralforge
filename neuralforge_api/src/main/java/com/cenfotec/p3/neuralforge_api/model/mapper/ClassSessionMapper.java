package com.cenfotec.p3.neuralforge_api.model.mapper;

import com.cenfotec.p3.neuralforge_api.model.entity.ClassSessionEntity;
import com.cenfotec.p3.neuralforge_api.model.entity.CourseWeekEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.ClassSessionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between ClassSessionEntity and ClassSessionResource.
 * Ensures consistent data transformation between the database entity and the API resource.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Component
public class ClassSessionMapper {

    @Autowired
    private CourseTopicMapper courseTopicMapper;

    /**
     * Converts a ClassSessionEntity into a ClassSessionResource.
     *
     * @param entity The ClassSessionEntity to be mapped.
     * @return A ClassSessionResource containing the mapped data.
     */
    public ClassSessionResource mapToResource(ClassSessionEntity entity) {
        if (entity == null) {
            return null;
        }

        return ClassSessionResource.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .topics(entity.getTopics() != null ?
                        entity.getTopics().stream()
                                .map(courseTopicMapper::mapToResource)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    /**
     * Converts a ClassSessionResource into a ClassSessionEntity.
     *
     * @param resource The ClassSessionResource to be mapped.
     * @return A ClassSessionEntity containing the mapped data.
     */
    public ClassSessionEntity mapToEntity(ClassSessionResource resource) {
        if (resource == null) {
            return null;
        }

        return mapToEntity(resource, null);
    }
    
    /**
     * Converts a ClassSessionResource into a ClassSessionEntity with a specified parent week.
     *
     * @param resource The ClassSessionResource to be mapped.
     * @param courseWeek The parent CourseWeekEntity to associate with this session.
     * @return A ClassSessionEntity containing the mapped data.
     */
    public ClassSessionEntity mapToEntity(ClassSessionResource resource, CourseWeekEntity courseWeek) {
        if (resource == null) {
            return null;
        }

        ClassSessionEntity entity = ClassSessionEntity.builder()
                .id(resource.getId())
                .dayOfWeek(resource.getDayOfWeek())
                .courseWeek(courseWeek)
                .topics(new ArrayList<>())
                .build();
                
        if (resource.getTopics() != null) {
            entity.setTopics(
                resource.getTopics().stream()
                    .map(topicResource -> courseTopicMapper.mapToEntity(topicResource, entity))
                    .collect(Collectors.toList())
            );
        }
        
        return entity;
    }
} 