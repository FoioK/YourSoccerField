import { Component, OnInit } from "@angular/core";
import { CalendarEvent } from "angular-calendar";
import { WeekView, WeekViewHourColumn, DayViewHour } from "calendar-utils";
import { compareDate } from "./custom-methods/compare-date";

@Component({
  selector: "app-calendar",
  templateUrl: "./calendar.component.html",
  styleUrls: ["./calendar.component.css"]
})
export class CalendarComponent implements OnInit {
  constructor() {}

  view: string = "month";

  viewDate: Date = new Date();

  events: CalendarEvent[] = [];

  clickedDate: Date;

  ngOnInit() {}

  private setDisableHours(data: WeekView) {
    this.getData(data.hourColumns);
  }

  private getData(data: WeekViewHourColumn[]) {
    let data1: DayViewHour[];

    data.forEach(e => {
      if (compareDate(this.viewDate, e.date)) {
        e.hours.forEach(e => {
          e.segments.forEach(e => {
            e.cssClass = 'cal-day-segment-disabled';
          });
        });
      }
    });
  }
}
