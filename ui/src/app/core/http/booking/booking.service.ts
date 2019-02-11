import {Injectable} from '@angular/core';
import {SoccerField} from '../../../shared/models/soccer-field';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Configuration} from '../../../configs/configuration';
import {Reservation} from '../../../shared/models/reservation';
import {catchError} from 'rxjs/operators';
import {HeaderService} from "../../services/header.service";
import {ApiRoutes, PATH_SOCCER_FIELD_ID} from "../../../configs/api-routes";

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  soccerfieldToBook: SoccerField;
  private isBookedSubject = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private configuration: Configuration,
  ) {
  }

  getReservationsForSoccerfield(id: number): Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(
      this.configuration.apiServer +
      ApiRoutes.SOCCER_FIELDS_BOOKINGS.replace(PATH_SOCCER_FIELD_ID, (id || "").toLocaleString()),
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
      ApiRoutes.BOOKINGS,
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
