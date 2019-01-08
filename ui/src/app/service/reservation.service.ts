import { Injectable } from "@angular/core";
import { SoccerField } from "../model/soccer-field";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Configuration } from "./configuration";
import { ApiMapping } from "./api-mapping";

@Injectable({
  providedIn: "root"
})
export class ReservationService {
  soccerfieldToBook: SoccerField;
  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping
  ) {}

  getSoccerfieldById(id: string): Observable<SoccerField> {
    return this.http.get<SoccerField>(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + id,
      {
        headers: Configuration.getJSONContentTypeWithToken()
      }
    );
  }
}
