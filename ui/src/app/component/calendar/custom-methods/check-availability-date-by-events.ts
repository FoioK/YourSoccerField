import { CalendarEvent } from 'angular-calendar';

export function checkAvailabilityDateByEvents(
  clickedDate: Date,
  events: CalendarEvent[]
): boolean {
  let result: boolean = true;
  const clickedData: Date = new Date(clickedDate);
  const eventsData: CalendarEvent[] = events;

  eventsData.forEach(event => {
    if (
      clickedData > event.start &&
      clickedData < event.end &&
      event.meta.id !== -1 // default id for current clicked date
    ) {
      result = false;
    }
  });
  return result;
}
