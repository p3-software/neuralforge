package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource class representing a class session within a course week.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSessionResource {

    /**
     * Unique identifier for the class session.
     */
    private String id;
    
    /**
     * Day of the week for this class session.
     */
    private DayOfWeek dayOfWeek;
    
    /**
     * List of topics to be covered in this session.
     */
    @Builder.Default
    private List<CourseTopicResource> topics = new ArrayList<>();
} 