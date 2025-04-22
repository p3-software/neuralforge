import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDividerModule } from '@angular/material/divider';
import { Quiz } from '../../../models/quiz.model';
import { QuizService } from '../../../services/quiz.service';
import { AlertService } from '../../../services/alert.service';

@Component({
  selector: 'app-quiz-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatTooltipModule,
    MatDividerModule
  ],
  templateUrl: './quiz-detail.component.html',
  styleUrls: ['./quiz-detail.component.scss']
})
export class QuizDetailComponent implements OnInit {
  quizId!: string;
  projectId!: string;
  quiz: Quiz | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('quizId');
      const projectId = params.get('id');
      
      if (id && projectId) {
        this.quizId = id;
        this.projectId = projectId;
        this.loadQuiz();
      } else {
        this.router.navigate(['/projects']);
      }
    });
  }

  loadQuiz(): void {
    this.isLoading = true;
    this.error = null;

    this.quizService.getQuiz(this.quizId).subscribe({
      next: (quiz) => {
        this.quiz = quiz;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Failed to load quiz. Please try again.';
        this.isLoading = false;
        console.error('Error loading quiz:', err);
      }
    });
  }

  deleteQuiz(): void {
    if (confirm(`Are you sure you want to delete the quiz "${this.quiz?.title}"?`)) {
      this.quizService.deleteQuiz(this.quizId).subscribe({
        next: () => {
          this.alertService.displayAlert('success', 'Quiz deleted successfully');
          this.navigateBack();
        },
        error: (err) => {
          this.alertService.displayAlert('error', 'Failed to delete quiz. Please try again.');
          console.error('Error deleting quiz:', err);
        }
      });
    }
  }

  navigateBack(): void {
    this.router.navigate(['/projects', this.projectId]);
  }

  formatDate(date: Date | undefined): string {
    return date ? new Date(date).toLocaleDateString() : '';
  }

  // This will be used when implementing the quiz taking functionality
  startQuiz(): void {
    this.router.navigate(['/projects', this.projectId, 'quiz', this.quizId, 'attempt']);
  }
}
