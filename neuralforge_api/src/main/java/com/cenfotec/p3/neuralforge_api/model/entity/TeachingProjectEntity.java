package com.cenfotec.p3.neuralforge_api.model.entity;

import com.cenfotec.p3.neuralforge_api.model.enums.ProjectTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity class representing a teaching project in the system.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@Entity
@Table(name = "teaching_projects")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("TEACHING")
public class TeachingProjectEntity extends ProjectEntity {

    @PrePersist
    public void prePersist() {
        super.setProjectType(ProjectTypeEnum.TEACHING);
    }

    /**
     * The days of the week when classes will be held.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "selected_days_id", referencedColumnName = "id")
    private SelectedDaysEntity selectedDays;

    /**
     * Number of hours per class session.
     */
    private Integer dailyHours;

    /**
     * Total number of weeks for this teaching project.
     */
    private Integer weeksCount;

    /**
     * Date when the course starts.
     */
    private Date startDate;

    /**
     * Date when the course ends.
     */
    private Date endDate;
    
    /**
     * List of weeks in this teaching project's schedule.
     */
    @OneToMany(mappedBy = "teachingProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseWeekEntity> weeks = new ArrayList<>();
} 