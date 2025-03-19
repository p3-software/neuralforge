import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from '../../components/post-card/post-card.component';
import { GestionCardComponent } from '../../components/gestion-card/gestion-card.component';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [PostCardComponent, GestionCardComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {}