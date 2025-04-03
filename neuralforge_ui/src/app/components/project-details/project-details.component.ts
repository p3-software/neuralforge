import { CommonModule } from "@angular/common";
import {
  Component,
  ContentChild,
  EventEmitter,
  Input,
  Output,
  TemplateRef,
} from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatTabsModule } from "@angular/material/tabs";
import { IProject } from "../../interfaces";

@Component({
  selector: "app-project-details",
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    MatSlideToggleModule,
  ],
  templateUrl: "./project-details.component.html",
  styleUrls: ["./project-details.component.scss"],
})
export class ProjectDetailsComponent {
  @Input() project: IProject | null = null;
  @Input() isLoading = false;
  @Input() hasError = false;
  @Input() errorMessage = "";
  @Input() showNotificationsToggle = false;
  @Input() notificationsEnabled = false;
  @ContentChild("overviewContent") overviewContent!: TemplateRef<any>;
  @ContentChild("generatedContent") generatedContent!: TemplateRef<any>;
  @ContentChild("configurationContent") configurationContent!: TemplateRef<any>;

  @Output() notificationsToggle = new EventEmitter<void>();
  @Output() editProject = new EventEmitter<void>();
  @Output() deleteProject = new EventEmitter<void>();
}
