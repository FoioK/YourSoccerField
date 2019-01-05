import { Injectable } from "@angular/core";
import { SoccerField } from "../model/soccer-field";

@Injectable({
  providedIn: "root"
})
export class ReservationService {
  soccerfieldToBook: SoccerField;
  constructor() {}

  setSoccerfieldToBook(data: SoccerField): void {
    this.soccerfieldToBook = data;
  }
}
