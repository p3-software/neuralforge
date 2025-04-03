import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { LayoutService } from "../../services/layout.service";
import { SvgIconComponent } from "../svg-icon/svg-icon.component";
import { SidebarComponent } from "./elements/sidebar/sidebar.component";

@Component({
  selector: "app-layout",
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent, SvgIconComponent],
  templateUrl: "./app-layout.component.html",
  styleUrl: "./app-layout.component.scss",
})
export class AppLayoutComponent {
  public title?: string;

  constructor(public layoutService: LayoutService) {
    this.layoutService.title.subscribe((title) => (this.title = title));
  }
}
