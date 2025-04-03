import { CommonModule, DatePipe } from "@angular/common";
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { ITeachingProject } from "../../interfaces";

interface IWeek {
  weekNumber: number;
  startDate: Date;
  endDate: Date;
  days: {
    date: Date;
    isTeachingDay: boolean;
    topics: string[];
  }[];
}

@Component({
  selector: "app-teaching-calendar",
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    DatePipe,
  ],
  templateUrl: "./teaching-calendar.component.html",
  styleUrls: ["./teaching-calendar.component.scss"],
})
export class TeachingCalendarComponent {
  @Input() project: ITeachingProject | null = null;
  @Input() calendarStartDate: Date = new Date();
  @Output() calendarStartDateChange = new EventEmitter<Date>();

  weeks: IWeek[] = [];

  ngOnInit() {
    this.generateWeeks();
  }

  ngOnChanges() {
    this.generateWeeks();
  }

  private generateWeeks() {
    if (!this.project?.startDate || !this.project?.endDate) return;

    const startDate = new Date(this.project.startDate);
    const endDate = new Date(this.project.endDate);
    const selectedDays = Object.entries(this.project.selectedDays)
      .filter(([_, value]) => value)
      .map(([key]) => key.toLowerCase());

    this.weeks = [];
    let currentDate = new Date(startDate);
    let weekNumber = 1;

    while (currentDate <= endDate) {
      const weekStart = new Date(currentDate);
      const weekEnd = new Date(currentDate);
      weekEnd.setDate(weekEnd.getDate() + 6);

      if (weekEnd > endDate) {
        weekEnd.setTime(endDate.getTime());
      }

      const days: IWeek["days"] = [];
      const weekDate = new Date(weekStart);

      while (weekDate <= weekEnd) {
        const dayName = weekDate
          .toLocaleDateString("en-US", { weekday: "long" })
          .toLowerCase();
        const isTeachingDay = selectedDays.includes(dayName);

        days.push({
          date: new Date(weekDate),
          isTeachingDay,
          topics: isTeachingDay ? [`Topic for ${dayName}`] : [], // This would come from your backend
        });

        weekDate.setDate(weekDate.getDate() + 1);
      }

      this.weeks.push({
        weekNumber,
        startDate: weekStart,
        endDate: weekEnd,
        days,
      });

      currentDate.setDate(currentDate.getDate() + 7);
      weekNumber++;
    }
  }

  getWeekRange(week: IWeek): string {
    return `${week.startDate.toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
    })} - ${week.endDate.toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
    })}`;
  }

  getDayName(date: Date): string {
    return date.toLocaleDateString("en-US", { weekday: "short" });
  }

  getCurrentMonth(): string {
    return this.calendarStartDate.toLocaleDateString("en-US", {
      month: "long",
      year: "numeric",
    });
  }

  getCalendarDays() {
    if (!this.project) return [];

    const selectedDays = Object.entries(this.project.selectedDays)
      .filter(([_, value]) => value)
      .map(([key]) => key.toLowerCase());

    const startDate = new Date(this.project.startDate || "");
    const endDate = new Date(this.project.endDate || "");
    const days: {
      date: Date | null;
      isTeachingDay: boolean;
      isToday: boolean;
      isWeekStart: boolean;
      isWeekEnd: boolean;
    }[] = [];

    const year = this.calendarStartDate.getFullYear();
    const month = this.calendarStartDate.getMonth();
    const startOfMonth = new Date(year, month, 1);
    const endOfMonth = new Date(year, month + 1, 0);
    const weekdayIndex = (startOfMonth.getDay() + 6) % 7;

    for (let i = 0; i < weekdayIndex; i++) {
      days.push({
        date: null,
        isTeachingDay: false,
        isToday: false,
        isWeekStart: false,
        isWeekEnd: false,
      });
    }

    for (let d = 1; d <= endOfMonth.getDate(); d++) {
      const date = new Date(year, month, d);
      const dayName = date
        .toLocaleDateString("en-US", { weekday: "long" })
        .toLowerCase();
      const isAfterEndDate = date > endDate;
      const isBeforeStartDate = date < startDate;
      const isToday = date.toDateString() === new Date().toDateString();
      const isTeachingDay =
        !isAfterEndDate && !isBeforeStartDate && selectedDays.includes(dayName);
      const isWeekStart = d === 1 || date.getDay() === 1;
      const isWeekEnd = d === endOfMonth.getDate() || date.getDay() === 0;

      days.push({
        date,
        isTeachingDay,
        isToday,
        isWeekStart,
        isWeekEnd,
      });
    }

    return days;
  }

  goForward() {
    const endDate = new Date(this.project?.endDate || "");
    const currentMonth = this.calendarStartDate.getMonth();
    const currentYear = this.calendarStartDate.getFullYear();
    const nextDate = new Date(currentYear, currentMonth + 1, 1);

    if (
      nextDate.getFullYear() < endDate.getFullYear() ||
      (nextDate.getFullYear() === endDate.getFullYear() &&
        nextDate.getMonth() <= endDate.getMonth())
    ) {
      this.calendarStartDateChange.emit(nextDate);
    }
  }

  goBack() {
    const startDate = new Date(this.project?.startDate || "");
    const prevMonthDate = new Date(
      this.calendarStartDate.getFullYear(),
      this.calendarStartDate.getMonth() - 1,
      1
    );

    if (
      prevMonthDate.getFullYear() > startDate.getFullYear() ||
      (prevMonthDate.getFullYear() === startDate.getFullYear() &&
        prevMonthDate.getMonth() >= startDate.getMonth())
    ) {
      this.calendarStartDateChange.emit(prevMonthDate);
    }
  }

  resetToToday() {
    this.calendarStartDateChange.emit(new Date());
  }

  isNextMonthBeyondEndDate(): boolean {
    const endDate = new Date(this.project?.endDate || "");
    const nextMonthDate = new Date(
      this.calendarStartDate.getFullYear(),
      this.calendarStartDate.getMonth() + 1,
      1
    );

    return (
      nextMonthDate.getFullYear() > endDate.getFullYear() ||
      (nextMonthDate.getFullYear() === endDate.getFullYear() &&
        nextMonthDate.getMonth() > endDate.getMonth())
    );
  }

  isPrevMonthBeforeStartDate(): boolean {
    const startDate = new Date(this.project?.startDate || "");
    const prevMonthDate = new Date(
      this.calendarStartDate.getFullYear(),
      this.calendarStartDate.getMonth() - 1,
      1
    );

    return (
      prevMonthDate.getFullYear() < startDate.getFullYear() ||
      (prevMonthDate.getFullYear() === startDate.getFullYear() &&
        prevMonthDate.getMonth() < startDate.getMonth())
    );
  }
}
