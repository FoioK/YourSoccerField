import { Component, OnInit, Input } from "@angular/core";
import { SoccerField } from "../../model/soccer-field";
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";
import { AppRoute } from "../../module/app-route";
import { ReservationService } from '../../service/reservation.service';


@Component({
  selector: "app-mini-socerfield",
  templateUrl: "./mini-socerfield.component.html",
  styleUrls: ["./mini-socerfield.component.css"]
})
export class MiniSocerfieldComponent implements OnInit {
  @Input()
  field: SoccerField;
  @Input()
  private even: boolean = false;

  constructor(private userService: UserService, private router: Router, private reservation: ReservationService) {}

  ngOnInit() {}

  private book(data: SoccerField) {
    this.reservation.setSoccerfieldToBook(data);
    this.router.navigate([AppRoute.reservation, data.id]);
  }
}
