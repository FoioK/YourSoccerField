import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BookingService} from '../../core/http/booking/booking.service';
import {SoccerFieldModel} from '../../shared/models/soccer-field.model';
import {ReservationModel} from 'src/app/shared/models/reservation.model';
import {DatePipe} from '@angular/common';
import {HttpErrorResponse} from '@angular/common/http';
import {SoccerFieldService} from '../../core/http/soccer-field/soccer-field.service';
import {SessionService} from "../../core/services/session.service";

@Component({
  selector: 'app-details-soccerfield',
  templateUrl: './details-soccerfield.component.html',
  styleUrls: ['./details-soccerfield.component.css']
})
export class DetailsSoccerfieldComponent implements OnInit {
  soccerfieldToBook: SoccerFieldModel;
  soccerFieldId: string;
  toBookingStart: Date = new Date();
  toBookingEnd: Date = new Date();
  userCode: number;
  isBooking: boolean = false;
  isPaid: boolean = false;
  paymentCode: string;
  copyText: string = 'Copy';
  errorMsg: string;

  constructor(
    private route: ActivatedRoute,
    private reservationService: BookingService,
    private soccerFieldService: SoccerFieldService,
    private sessionService: SessionService,
    private datePipe: DatePipe
  ) {
  }

  ngOnInit() {
    this.getSoccerfieldById();
    this.userCode = this.sessionService.getLoggedUserCode();
  }

  private getSoccerfieldById(): void {
    this.soccerFieldId = this.route.snapshot.paramMap.get('id');
    this.soccerFieldService
      .getSoccerFieldById(parseInt(this.soccerFieldId, 10))
      .subscribe(result => {
        this.soccerfieldToBook = result;
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

  private generatePaymentCode(data: string): string {
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

  private setReservation(): void {
    const reservation: ReservationModel = {
      executionTime: '01:30',
      payed: false,
      soccerField: parseInt(this.soccerFieldId, 10),
      startDate: this.transformDateToApiFormat(this.toBookingStart),
      userCode: this.userCode
    };
    this.reservationService
      .createReservation(reservation)
      .subscribe(
        result => {
          this.paymentCode = this.generatePaymentCode(
            result.body.id.toString()
          );
          this.reservationService.setWasBooked(true);
          this.isPaid = true;
        },
        (err: HttpErrorResponse) => {
          if (err.status === 409) {
            this.reservationService.setWasBooked(true);
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
