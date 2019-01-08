import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ReservationService } from "../../service/reservation.service";
import { SoccerField } from "../../model/soccer-field";
@Component({
  selector: "app-details-soccerfield",
  templateUrl: "./details-soccerfield.component.html",
  styleUrls: ["./details-soccerfield.component.css"]
})
export class DetailsSoccerfieldComponent implements OnInit {
  soccerfieldToBook: SoccerField;
  currentId: string;

  constructor(
    private route: ActivatedRoute,
    private reservation: ReservationService
  ) {}

  ngOnInit() {
    this.getSoccerfieldById();
    setTimeout(() => {
      console.log(this.soccerfieldToBook);
    }, 5000);
  }

  private getSoccerfieldById(): void {
    this.currentId = this.route.snapshot.paramMap.get("id");
    this.reservation.getSoccerfieldById(this.currentId).subscribe(result => {
      this.soccerfieldToBook = result;
    });
  }
}
