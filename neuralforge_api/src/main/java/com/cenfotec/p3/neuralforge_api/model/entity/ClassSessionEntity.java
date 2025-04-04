package com.cenfotec.p3.neuralforge_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a class session within a course week.
 * Contains information about a specific class session on a particular day.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@Entity
@Table(name = "class_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSessionEntity {

    /**
     * Unique identifier for the class session.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Course week this class session belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_week_id", nullable = false)
    private CourseWeekEntity courseWeek;

    /**
     * Day of the week for this class session.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    /**
     * List of topics to be covered in this session.
     */
    @OneToMany(mappedBy = "classSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CourseTopicEntity> topics = new ArrayList<>();
} 