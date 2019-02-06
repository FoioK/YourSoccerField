import { Injectable } from '@angular/core';
import { SoccerField } from '../model/soccer-field';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Configuration } from './configuration';
import { ApiMapping } from './api-mapping';
import { Reservation } from '../model/reservation';
import { catchError } from 'rxjs/operators';
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

  private isBookedSubject = new BehaviorSubject<boolean>(false);

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
      this.configuration.apiServer +
        this.apiMapping.soccerField_findById +
        id +
        this.apiMapping.booking_create,
      {
        headers: Configuration.getJSONContentTypeWithToken()
      }
    ).pipe(catchError(this.errorHandler));
  }

  createReservation(
    reservation: Reservation
  ): Observable<HttpResponse<Reservation>> {
    return this.http.post<Reservation>(
      this.configuration.apiServer +
        this.apiMapping.booking_create,
      reservation,
      {
        headers: Configuration.getJSONContentTypeWithToken(),
        observe: 'response'
      }
    ).pipe(catchError(this.errorHandler));
  }

  setWasBooked(isBooked: boolean) {
    this.isBookedSubject.next(isBooked);
  }

  checkWasBooked(): Observable<boolean> {
    return this.isBookedSubject.asObservable();
  }

  private errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }
}
