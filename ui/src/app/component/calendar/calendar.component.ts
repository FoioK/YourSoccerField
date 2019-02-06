import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { WeekView, WeekViewHourColumn } from 'calendar-utils';
import { compareDateIsLess } from './custom-methods/compare-date-is-less';
import { compareTime } from './custom-methods/compare-time';
import { checkAvailabilityDateByDisable } from './custom-methods/check-availability-date-by-disable';
import { colors } from './colors/color-events';
import { checkAvailabilityDateByEvents } from './custom-methods/check-availability-date-by-events';
import { Subject } from 'rxjs';
import { ReservationService } from '../../service/reservation.service';
import { addTimeToDate } from './custom-methods/add-time-to-date';
import { ActivatedRoute } from '@angular/router';
import { setNewCalendarEvent } from './custom-methods/set-new-calendar-event';
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  constructor(
    private reservationService: ReservationService,
    private route: ActivatedRoute
  ) {}

  @Output()
  errorMsg: string;
  @Output()
  toBooking: EventEmitter<any> = new EventEmitter<any>();

  view: string = 'day';

  soccerFieldId: string;
  chooseEventId: number = -1;

  currentId: number = -1;

  viewDate: Date = new Date();

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [];

  currentDayViewHour: WeekViewHourColumn[];
  clickedDate: Date;

  ngOnInit() {
    this.getBookedDate();
    this.reservationService.checkWasBooked().subscribe(result => {
      if (result) {
        this.getBookedDate();
        this.reservationService.setWasBooked(false);
      }
    });
  }

  private getBookedDate() {
    this.soccerFieldId = this.route.snapshot.paramMap.get('id');
    this.reservationService
      .getReservationsForSoccerfield(parseInt(this.soccerFieldId, 10))
      .subscribe(result => {
        this.events = [];
        result.forEach(e => {
          if (!compareDateIsLess(new Date(), new Date(e.startDate))) {
            setNewCalendarEvent(
              this.events,
              new Date(e.startDate),
              addTimeToDate(e.executionTime, new Date(e.startDate))
            );
          }
        });
      });
  }

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

  private selectDateToBook(data: Date) {
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
          this.chooseEventId
        )
      ) {
        this.events = this.events.filter(event => {
          return event.id !== this.chooseEventId;
        });
        this.events.push({
          id: this.chooseEventId,
          title: 'Your choice',
          color: colors.yellow,
          start: this.clickedDate,
          end: newDate
        });
        this.errorMsg = '';
        this.toBooking.emit({
          isBooking: true,
          start: this.clickedDate,
          end: newDate
        });
      } else {
        this.events = this.events.filter(event => {
          return event.id !== this.chooseEventId;
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
