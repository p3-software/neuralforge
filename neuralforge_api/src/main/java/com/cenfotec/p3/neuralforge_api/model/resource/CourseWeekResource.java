package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource class representing a course week within a teaching project schedule.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseWeekResource {

    /**
     * Unique identifier for the course week.
     */
    private String id;
    
    /**
     * Week number within the course (1-based).
     */
    private Integer weekNumber;
    
    /**
     * Class sessions scheduled within this week.
     */
    @Builder.Default
    private List<ClassSessionResource> classSessions = new ArrayList<>();
} 