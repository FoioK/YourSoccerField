import {CalendarEvent} from 'angular-calendar';
import {WeekViewHourColumn} from 'calendar-utils';

export function checkAvailabilityDateByEvents(
  dateStart: Date,
  dateEnd: Date,
  events: CalendarEvent[],
  currentDayViewHour: WeekViewHourColumn[],
  currentId: number
): boolean {
  let result: boolean = true;
  const dateStartData: Date = new Date(dateStart);
  const dateEndData: Date = new Date(dateEnd);
  const eventsData: CalendarEvent[] = events;
  const DayViewHourData: WeekViewHourColumn[] = currentDayViewHour;
  let DayEndHourData: Date;

  dateStartData.setHours(0, 0, 0, 0);
  let dateSegment: Date;

  // protection against going out of hours range
  DayViewHourData.forEach(hourColumns => {
    hourColumns.hours.forEach(hours => {
      hours.segments.forEach(segments => {
        dateSegment = new Date(segments.date);
        dateSegment.setHours(0, 0, 0, 0);
        if (dateSegment.getTime() === dateStartData.getTime()) {
          DayEndHourData = new Date(segments.date);
          DayEndHourData.setTime(DayEndHourData.getTime() + 1800000); // plus 30 mins to full hour
        }
      });
    });
  });

  eventsData.forEach(event => {
    if (
      (dateEndData > event.start &&
        dateEndData < event.end &&
        event.id !== currentId) || // default id for current clicked date
      dateEndData.getTime() > DayEndHourData.getTime()
    ) {
      result = false;
    }
  });

  return result;
}
