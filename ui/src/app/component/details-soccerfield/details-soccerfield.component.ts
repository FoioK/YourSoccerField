import { Component, OnInit } from '@angular/core';
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

  constructor(private route: ActivatedRoute, private reservation: ReservationService) { }

  ngOnInit() {
    console.log(this.route.snapshot.paramMap.get('id'));
    this.soccerfieldToBook = this.reservation.soccerfieldToBook;
    console.log(this.soccerfieldToBook);
  }

}
