import {WeekViewHourColumn} from 'calendar-utils';

export function checkAvailabilityDateByDisable(
  clickedDate: Date,
  currentDayViewHour: WeekViewHourColumn[],
  cssClassDisable: string
): boolean {
  let result: boolean = true;
  const clickedData: Date = new Date(clickedDate);
  const DayViewHourData: WeekViewHourColumn[] = currentDayViewHour;

  DayViewHourData.forEach(hourColumns => {
    hourColumns.hours.forEach(hours => {
      hours.segments.forEach(segments => {
        if (
          segments.date.getTime() === clickedData.getTime() &&
          segments.cssClass === cssClassDisable
        ) {
          result = false;
        }
      });
    });
  });
  return result;
}
