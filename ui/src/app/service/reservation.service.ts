import { Injectable } from '@angular/core';
import { SoccerField } from '../model/soccer-field';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Configuration } from './configuration';
import { ApiMapping } from './api-mapping';
import { Reservation } from '../model/reservation';

@Injectable({
  providedIn: 'root'
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

  getReservationsForSoccerfield(id: string): Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(
      this.configuration.apiServer + this.apiMapping.soccerField_findById + id + this.apiMapping.soccerField_reservationsById,
      {
        headers: Configuration.getJSONContentTypeWithToken()
      }
    );
  }
}
