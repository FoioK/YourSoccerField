import {CalendarEvent} from 'angular-calendar';
import {colors} from '../styless/color-events';

export function setNewCalendarEvent(
  calendar: CalendarEvent[],
  dateStart: Date,
  dateEnd: Date
): void {
  let currentId: string | number = 0;
  calendar.forEach(e => {
    if (e.id > currentId) {
      currentId = e.id;
    }
  });
  calendar.push({
    id: currentId + 1,
    title: 'Booked',
    color: colors.blue,
    start: new Date(dateStart),
    end: new Date(dateEnd)
  });
}
