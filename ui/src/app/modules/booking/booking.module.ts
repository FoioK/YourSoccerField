import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {BookingRoutingModule} from "./booking-routing.module";
import {CalendarComponent} from "./components/calendar/calendar.component";
import {CalendarHeaderComponent} from "./components/calendar-header/calendar-header.component";
import {CalendarDateFormatter, CalendarModule, DateAdapter} from "angular-calendar";
import {adapterFactory} from "angular-calendar/date-adapters/date-fns";
import {CustomDateFormatter} from "./utils/twenty-four-hours";
import {ClipboardModule} from "ngx-clipboard";
import {ConvertTime12To24FormatPipe} from "./pipe/convert-time12-to24-format.pipe";
import {DetailsSoccerfieldComponent} from "./pages/detail-soccer-field/details-soccerfield.component";

@NgModule({
  declarations: [
    DetailsSoccerfieldComponent,
    CalendarComponent,
    CalendarHeaderComponent,
    ConvertTime12To24FormatPipe,
  ],
  imports: [
    SharedModule,
    BookingRoutingModule,

    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }, {
      dateFormatter: {
        provide: CalendarDateFormatter,
        useClass: CustomDateFormatter
      }
    }),
    ClipboardModule,
  ],
  providers: [],
})
export class BookingModule {

}
