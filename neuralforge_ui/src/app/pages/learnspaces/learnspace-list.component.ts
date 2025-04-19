import { Component, OnInit } from '@angular/core';
import { LearnspaceService } from '../../services/learnspace.service';
import { Learnspace } from '../../interfaces';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-learnspace-list',
  templateUrl: './learnspace-list.component.html',
  styleUrls: ['./learnspace-list.component.scss'],
  imports: [CommonModule, RouterModule]
})
export class LearnspaceListComponent implements OnInit {
  learnspaces: Learnspace[] = [];

  constructor(private learnspaceService: LearnspaceService) {}

  ngOnInit(): void {
    this.learnspaceService.getAll().subscribe((data: Learnspace[]) => {
      this.learnspaces = data;
    });
  }
}
