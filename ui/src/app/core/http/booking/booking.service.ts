import { Injectable } from '@angular/core';
import { SoccerField } from '../../../shared/models/soccer-field';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Configuration } from '../../../configs/configuration';
import { ApiMapping } from '../../../configs/api-mapping';
import { Reservation } from '../../../shared/models/reservation';
import { catchError } from 'rxjs/operators';
import {HeaderService} from "../../services/header.service";
@Injectable({
  providedIn: 'root'
})
export class BookingService {
  soccerfieldToBook: SoccerField;
  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping
  ) {}

  private isBookedSubject = new BehaviorSubject<boolean>(false);

  getReservationsForSoccerfield(id: number): Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(
      this.configuration.apiServer +
        this.apiMapping.soccerField_findById + id +
        this.apiMapping.booking_create,
      {
        headers: HeaderService.getJSONContentTypeWithToken()
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
        headers: HeaderService.getJSONContentTypeWithToken(),
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
