package com.cenfotec.p3.neuralforge_api.model.resource;

import com.cenfotec.p3.neuralforge_api.model.enums.ProjectTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * Resource class representing a teaching project in the system.
 * Extends the base ProjectResource with teaching-specific attributes.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TeachingProjectResource extends ProjectResource {

    /**
     * The days of the week selected for teaching sessions.
     */
    private SelectedDaysResource selectedDays;
    
    /**
     * Number of hours allocated per day for teaching activities.
     */
    private Integer dailyHours;
    
    /**
     * Total duration of the teaching project in weeks.
     */
    private Integer weeksCount;
    
    /**
     * The date when the teaching project begins.
     */
    private Date startDate;
    
    /**
     * The date when the teaching project ends.
     */
    private Date endDate;
}