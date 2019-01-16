import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { WeekView, WeekViewHourColumn } from 'calendar-utils';
import { compareDateIsLess } from './custom-methods/compare-date-is-less';
import { compareTime } from './custom-methods/compare-time';
import { checkAvailabilityDateByDisable } from './custom-methods/check-availability-date-by-disable';
import { colors } from './colors/color-events';
import { checkAvailabilityDateByEvents } from './custom-methods/check-availability-date-by-events';
import { Subject } from 'rxjs';
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  constructor() {}
  @Output()
  errorMsg: string;
  @Output()
  toBooking: EventEmitter<any> = new EventEmitter<any>();
  view: string = 'week';

  currentId: number = -1;

  viewDate: Date = new Date();

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [
    {
      title: 'Booked',
      color: colors.blue,
      start: new Date(2019, 0, 15, 9, 0, 0, 0),
      end: new Date(2019, 0, 15, 10, 30, 0, 0),
      meta: {
        id: 1
      }
    }
  ];

  currentDayViewHour: WeekViewHourColumn[];
  clickedDate: Date;
  ngOnInit() {}

  private setDisableHours(data: WeekView) {
    this.currentDayViewHour = data.hourColumns;
    data.hourColumns.forEach(hourColumns => {
      if (compareDateIsLess(new Date(), hourColumns.date)) {
        hourColumns.hours.forEach(hours => {
          hours.segments.forEach(segments => {
            segments.cssClass = 'cal-day-segment-disabled';
          });
        });
      } else {
        hourColumns.hours.forEach(hours => {
          hours.segments.forEach(segments => {
            if (compareTime(new Date(), segments.date)) {
              segments.cssClass = 'cal-day-segment-disabled';
            }
          });
        });
      }
    });
  }

  private hourSegmentClicked(data: Date) {
    if (
      checkAvailabilityDateByDisable(
        data,
        this.currentDayViewHour,
        'cal-day-segment-disabled'
      )
    ) {
      this.clickedDate = new Date(data);
      const newDate = new Date(data);
      newDate.setTime(newDate.getTime() + 5400000);
      if (
        checkAvailabilityDateByEvents(
          this.clickedDate,
          newDate,
          this.events,
          this.currentDayViewHour,
          this.currentId
        )
      ) {
        this.events = this.events.filter(event => {
          return event.meta.id !== this.currentId;
        });
        this.events.push({
          title: 'Your choice',
          color: colors.yellow,
          start: this.clickedDate,
          end: newDate,
          meta: {
            id: this.currentId
          }
        });
        this.errorMsg = '';
        this.toBooking.emit({
          isBooking: true,
          start: this.clickedDate,
          end: newDate
        });
      } else {
        this.events = this.events.filter(event => {
          return event.meta.id !== this.currentId;
        });
        this.toBooking.emit({
          isBooking: false
        });
        this.errorMsg =
          'Your choosen date overlap on other reservation or going outside of hours range';
      }
    }
    this.refresh.next();
  }
}
