package com.cenfotec.p3.neuralforge_api.controller;

import com.cenfotec.p3.neuralforge_api.model.resource.QuizResource;
import com.cenfotec.p3.neuralforge_api.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller responsible for handling quiz-related requests.
 * Provides endpoints for creating, retrieving, and deleting quizzes.
 *
 * @author Enrique Alpízar
 * @version 1.0
 */
@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    /**
     * Creates a new quiz.
     *
     * @param quizResource The quiz resource to create.
     * @return A ResponseEntity containing the created quiz.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResource> createQuiz(@Valid @RequestBody QuizResource quizResource) {
        QuizResource createdQuiz = quizService.createQuiz(quizResource);
        return ResponseEntity.ok(createdQuiz);
    }

    /**
     * Generates a quiz for a project using AI based on project materials.
     *
     * @param projectId The ID of the project to generate a quiz for.
     * @param title The title of the quiz.
     * @param description The description of the quiz.
     * @param questionCount The number of questions to generate.
     * @return A ResponseEntity containing the generated quiz.
     */
    @PostMapping("/generate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResource> generateQuiz(
            @RequestParam String projectId,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "5") int questionCount) {
        
        QuizResource generatedQuiz = quizService.generateQuiz(projectId, title, description, questionCount);
        return ResponseEntity.ok(generatedQuiz);
    }

    /**
     * Retrieves a quiz by its ID.
     *
     * @param id The ID of the quiz to retrieve.
     * @return A ResponseEntity containing the quiz.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResource> getQuiz(@PathVariable String id) {
        QuizResource quiz = quizService.getQuiz(id);
        return ResponseEntity.ok(quiz);
    }

    /**
     * Retrieves all quizzes for a specific project.
     *
     * @param projectId The ID of the project to retrieve quizzes for.
     * @return A ResponseEntity containing the list of quizzes.
     */
    @GetMapping("/project/{projectId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuizResource>> getQuizzesByProject(@PathVariable String projectId) {
        List<QuizResource> quizzes = quizService.getQuizzesByProject(projectId);
        return ResponseEntity.ok(quizzes);
    }

    /**
     * Deletes a quiz by ID (soft delete).
     *
     * @param id The ID of the quiz to delete.
     * @return A ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}
