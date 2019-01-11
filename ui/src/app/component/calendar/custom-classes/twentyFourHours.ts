import { CalendarNativeDateFormatter, DateFormatterParams, CalendarModule } from "angular-calendar";

export class CustomDateFormatter extends CalendarNativeDateFormatter {

  public weekViewHour({date, locale}: DateFormatterParams): string {
    return new Intl.DateTimeFormat('ca', {
      hour: 'numeric',
      minute: 'numeric'
    }).format(date);
  }

}
