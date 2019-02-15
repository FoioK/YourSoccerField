import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BookingService} from '../../core/http/booking/booking.service';
import {SoccerFieldModel} from '../../shared/models/soccer-field.model';
import {BookingModel} from 'src/app/shared/models/booking.model';
import {DatePipe} from '@angular/common';
import {HttpErrorResponse} from '@angular/common/http';
import {SoccerFieldService} from '../../core/http/soccer-field/soccer-field.service';
import {SessionService} from "../../core/services/session.service";

@Component({
  selector: 'app-details-soccerField',
  templateUrl: './details-soccer-field.component.html',
  styleUrls: ['./details-soccer-field.component.css']
})
export class DetailsSoccerFieldComponent implements OnInit {
  private soccerFieldToBook: SoccerFieldModel;
  private soccerFieldId: string;
  private toBookingStart: Date = new Date();
  private toBookingEnd: Date = new Date();
  private userCode: number;
  private isBooking: boolean = false;
  private isPaid: boolean = false;
  private paymentCode: string;
  private copyText: string = 'Copy';
  private errorMsg: string;

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private soccerFieldService: SoccerFieldService,
    private sessionService: SessionService,
    private datePipe: DatePipe
  ) {
  }

  ngOnInit() {
    this.getSoccerFieldById();
    this.userCode = this.sessionService.getLoggedUserCode();
  }

  private getSoccerFieldById(): void {
    this.soccerFieldId = this.route.snapshot.paramMap.get('id');
    this.soccerFieldService
      .getSoccerFieldById(parseInt(this.soccerFieldId, 10))
      .subscribe(result => {
        this.soccerFieldToBook = result;
      });
  }

  private setDateToBooking(data: any): void {
    this.errorMsg = '';
    this.isPaid = false;
    this.isBooking = data.isBooking;

    if (this.isBooking) {
      this.toBookingStart = new Date(data.start);
      this.toBookingEnd = new Date(data.end);
    }
  }

  private transformDateToApiFormat(date: Date): string {
    // result -> yyyy-mm-ddThh:mm in 24 hours format
    const dataDate = new Date(date);

    return (
      this.datePipe.transform(dataDate, 'yyyy-MM-dd') +
      'T' +
      dataDate
        .toString()
        .split(' ')[4]
        .slice(0, 5)
    );
  }

  private static generatePaymentCode(data: string): string {
    let result = '';
    for (let i = 0; i < btoa(data).length; i++) {
      result += btoa(data)
        .charCodeAt(i)
        .toString();
      if (i + 1 < btoa(data).length) {
        result += '/';
      }
    }

    return result;
  }

  private setBooking(): void {
    const booking: BookingModel = {
      executionTime: '01:30',
      payed: false,
      soccerField: parseInt(this.soccerFieldId, 10),
      startDate: this.transformDateToApiFormat(this.toBookingStart),
      userCode: this.userCode
    };

    this.bookingService
      .createBooking(booking)
      .subscribe(
        result => {
          this.paymentCode = DetailsSoccerFieldComponent.generatePaymentCode(
            result.body.id.toString()
          );
          this.bookingService.setWasBooked(true);
          this.isPaid = true;
        },
        (err: HttpErrorResponse) => {
          if (err.status === 409) {
            this.bookingService.setWasBooked(true);
            this.isPaid = false;
            this.isBooking = false;
            this.errorMsg = 'Is another booking on this time, please try again';
          }
        }
      );
  }

  private copyCodeToClipboard() {
    this.copyText = 'Copied';
  }
}
