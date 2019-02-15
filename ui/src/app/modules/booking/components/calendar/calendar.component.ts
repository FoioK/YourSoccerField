import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {CalendarEvent} from 'angular-calendar';
import {WeekView, WeekViewHourColumn} from 'calendar-utils';
import {compareDateIsLess} from '../../functions/compare-date-is-less';
import {compareTime} from '../../functions/compare-time';
import {checkAvailabilityDateByDisable} from '../../functions/check-availability-date-by-disable';
import {colors} from '../../styless/color-events';
import {checkAvailabilityDateByEvents} from '../../functions/check-availability-date-by-events';
import {Subject} from 'rxjs';
import {BookingService} from '../../../../core/http/booking/booking.service';
import {addTimeToDate} from '../../functions/add-time-to-date';
import {ActivatedRoute} from '@angular/router';
import {setNewCalendarEvent} from '../../functions/set-new-calendar-event';
import {SoccerFieldService} from "../../../../core/http/soccer-field/soccer-field.service";

@Component({
  selector: 'app-calendar',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  @Output()
  errorMsg: string;
  @Output()

  private toBooking: EventEmitter<any> = new EventEmitter<any>();
  private view: string = 'day';
  private soccerFieldId: string;
  private chooseEventId: number = -1;
  private viewDate: Date = new Date();
  private refresh: Subject<any> = new Subject();
  private events: CalendarEvent[] = [];
  private currentDayViewHour: WeekViewHourColumn[];
  private clickedDate: Date;

  constructor(
    private bookingService: BookingService,
    private soccerFieldService: SoccerFieldService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.getBookedDate();
    this.bookingService.checkWasBooked().subscribe(result => {
      if (result) {
        this.getBookedDate();
        this.bookingService.setWasBooked(false);
      }
    });
  }

  private getBookedDate() {
    this.soccerFieldId = this.route.snapshot.paramMap.get('id');
    this.soccerFieldService
      .getBookings(parseInt(this.soccerFieldId, 10))
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
