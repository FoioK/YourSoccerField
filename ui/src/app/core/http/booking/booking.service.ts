import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {API_SERVER} from '../../../configs/configuration';
import {BookingModel} from '../../../shared/models/booking.model';
import {catchError} from 'rxjs/operators';
import {HeaderService} from "../../services/header.service";
import {ApiRoute} from "../api.route";
import {SessionService} from "../../services/session.service";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  private isBookedSubject = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private sessionService: SessionService,
  ) {

  }

  createBooking(
    booking: BookingModel
  ): Observable<HttpResponse<BookingModel>> {
    return this.http.post<BookingModel>(
      API_SERVER + ApiRoute.BOOKINGS,
      booking,
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService),
        observe: 'response'
      }
    ).pipe(catchError(BookingService.errorHandler));
  }

  setWasBooked(isBooked: boolean) {
    this.isBookedSubject.next(isBooked);
  }

  checkWasBooked(): Observable<boolean> {
    return this.isBookedSubject.asObservable();
  }

  private static errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }

}
