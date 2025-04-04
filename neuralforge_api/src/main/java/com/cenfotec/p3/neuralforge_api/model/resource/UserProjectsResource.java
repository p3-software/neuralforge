package com.cenfotec.p3.neuralforge_api.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Resource class representing a combined response of all project types.
 * Used to return all types of projects in a single API call.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectsResource {
    /**
     * List of learning projects associated with the current user.
     */
    private List<LearningProjectResource> learningProjects;

    /**
     * List of teaching projects associated with the current user.
     */
    private List<TeachingProjectResource> teachingProjects;

    /**
     * List of programmed goal projects associated with the current user.
     */
    private List<ProgrammedGoalProjectResource> programmedGoalProjects;
} 