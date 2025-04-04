package com.cenfotec.p3.neuralforge_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a week within a teaching project's schedule.
 * Contains information about a specific week in the teaching project schedule.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Entity
@Table(name = "course_weeks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseWeekEntity {

    /**
     * Unique identifier for the course week.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Teaching project this week belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teaching_project_id", nullable = false)
    private TeachingProjectEntity teachingProject;

    /**
     * Week number within the course (1-based).
     */
    @Column(nullable = false)
    private Integer weekNumber;

    /**
     * Class sessions scheduled within this week.
     */
    @OneToMany(mappedBy = "courseWeek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ClassSessionEntity> classSessions = new ArrayList<>();
} 