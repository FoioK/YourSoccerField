import { Component, OnInit, Output, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationService } from '../../service/reservation.service';
import { SoccerField } from '../../model/soccer-field';
@Component({
  selector: 'app-details-soccerfield',
  templateUrl: './details-soccerfield.component.html',
  styleUrls: ['./details-soccerfield.component.css']
})
export class DetailsSoccerfieldComponent implements OnInit {
  soccerfieldToBook: SoccerField;
  soccerFieldId: string;
  toBookingStart: Date = new Date();
  toBookingEnd: Date = new Date();
  isBooking: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private reservation: ReservationService
  ) {}

  ngOnInit() {
    this.getSoccerfieldById();
  }

  private getSoccerfieldById(): void {
    this.soccerFieldId = this.route.snapshot.paramMap.get('id');
    this.reservation.getSoccerfieldById(this.soccerFieldId).subscribe(result => {
      this.soccerfieldToBook = result;
    });
  }

  private setDateToBooking(data: any): void {
    this.isBooking = data.isBooking;
    if (this.isBooking) {
      this.toBookingStart = new Date(data.start);
      this.toBookingEnd = new Date(data.end);
    }
  }
}
