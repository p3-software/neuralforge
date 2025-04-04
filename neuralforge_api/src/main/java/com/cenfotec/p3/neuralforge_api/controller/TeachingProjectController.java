package com.cenfotec.p3.neuralforge_api.controller;

import com.cenfotec.p3.neuralforge_api.model.resource.TeachingProjectResource;
import com.cenfotec.p3.neuralforge_api.service.TeachingProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing teaching projects.
 * 
 * @author Enrique Alpízar
 * @version 1.0
 */
@RestController
@RequestMapping("/teaching-projects")
public class TeachingProjectController {

    @Autowired
    private TeachingProjectService teachingProjectService;

    /**
     * Creates a new teaching project.
     *
     * @param teachingProject The teaching project resource to create.
     * @return The created teaching project resource.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeachingProjectResource> createTeachingProject(@Valid @RequestBody TeachingProjectResource teachingProject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teachingProjectService.createTeachingProject(teachingProject));
    }

    /**
     * Updates an existing teaching project.
     *
     * @param id The ID of the teaching project to update.
     * @param teachingProject The updated teaching project resource.
     * @return The updated teaching project resource.
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeachingProjectResource> updateTeachingProject(
            @PathVariable String id,
            @Valid @RequestBody TeachingProjectResource teachingProject) {
        return ResponseEntity.ok(teachingProjectService.updateTeachingProject(id, teachingProject));
    }

    /**
     * Retrieves a teaching project by its ID.
     *
     * @param id The ID of the teaching project to retrieve.
     * @return The teaching project resource.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeachingProjectResource> getTeachingProject(@PathVariable String id) {
        return ResponseEntity.ok(teachingProjectService.getTeachingProject(id));
    }

    /**
     * Retrieves all teaching projects.
     *
     * @return A list of teaching project resources.
     */
    @GetMapping
    public ResponseEntity<List<TeachingProjectResource>> getAllTeachingProjects() {
        return ResponseEntity.ok(teachingProjectService.getAllTeachingProjects());
    }

    /**
     * Retrieves all teaching projects created by the currently authenticated user.
     *
     * @return A list of teaching project resources.
     */
    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TeachingProjectResource>> getCurrentUserTeachingProjects() {
        return ResponseEntity.ok(teachingProjectService.getCurrentUserTeachingProjects());
    }

    /**
     * Deletes a teaching project by its ID.
     *
     * @param id The ID of the teaching project to delete.
     * @return A response with no content.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteTeachingProject(@PathVariable String id) {
        teachingProjectService.deleteTeachingProject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Generates a teaching schedule for a project using DeepSeek AI.
     *
     * @param teachingProjectId The ID of the teaching project for which to generate a schedule.
     * @return The teaching project resource with the generated schedule.
     */
    @PostMapping("/{teachingProjectId}/generate-schedule")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeachingProjectResource> generateTeachingSchedule(@PathVariable String teachingProjectId) {
        return ResponseEntity.ok(teachingProjectService.generateTeachingSchedule(teachingProjectId));
    }
} 