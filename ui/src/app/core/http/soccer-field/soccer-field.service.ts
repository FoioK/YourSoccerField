import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {SoccerFieldModel} from '../../../shared/models/soccer-field.model';
import {SearchModel} from '../../../shared/models/search.model';
import {SurfaceModel} from '../../../shared/models/surface.model';
import {HeaderService} from "../../services/header.service";
import {ApiRoute, PARAM_ENCODED_OBJECT, PATH_SOCCER_FIELD_ID, PATH_STREET} from "../api.route";
import {SessionService} from "../../services/session.service";
import {ReservationModel} from "../../../shared/models/reservation.model";
import {catchError} from "rxjs/operators";
import {API_SERVER} from "../../../configs/configuration";

@Injectable({
  providedIn: 'root'
})
export class SoccerFieldService {
  constructor(
    private http: HttpClient,
    private sessionService: SessionService,
  ) {

  }

  findAll(): Observable<Array<SoccerFieldModel>> {
    return this.http.get<Array<SoccerFieldModel>>(
      API_SERVER + ApiRoute.SOCCER_FIELDS,
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService)
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  findByAddressContains(street: string): Observable<Array<SoccerFieldModel>> {
    return this.http.get<Array<SoccerFieldModel>>(
      API_SERVER +
      ApiRoute.SOCCER_FIELDS_SEARCH_BY_STREET.replace(PATH_STREET, street),
      {
        headers: HeaderService.JSONContentType()
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  findByCustomCriteria(
    searchModel: SearchModel
  ): Observable<Array<SoccerFieldModel>> {
    const stringJson: string = JSON.stringify(searchModel);

    return this.http.get<Array<SoccerFieldModel>>(
      API_SERVER +
      ApiRoute.SOCCER_FIELDS_ADVANCED_SEARCH.replace(PARAM_ENCODED_OBJECT, btoa(stringJson)),
      {
        headers: HeaderService.JSONContentType()
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  getExampleTen(): Observable<Array<SoccerFieldModel>> {
    return this.http.get<Array<SoccerFieldModel>>(
      API_SERVER + ApiRoute.SOCCER_FIELDS_EXAMPLE_TEN,
      {
        headers: HeaderService.JSONContentType()
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  getBookings(id: number): Observable<Array<ReservationModel>> {
    return this.http.get<Array<ReservationModel>>(
      API_SERVER +
      ApiRoute.SOCCER_FIELDS_BOOKINGS.replace(PATH_SOCCER_FIELD_ID, (id || "").toLocaleString()),
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService)
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  getAllSurfaces(): Observable<Array<SurfaceModel>> {
    return this.http.get<Array<SurfaceModel>>(
      API_SERVER + ApiRoute.SURFACES,
      {
        headers: HeaderService.JSONContentType()
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  getSoccerFieldById(id: number): Observable<SoccerFieldModel> {
    return this.http.get<SoccerFieldModel>(
      API_SERVER + ApiRoute.SOCCER_FIELDS_WITH_ID.replace(PATH_SOCCER_FIELD_ID, (id || "").toLocaleString()),
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService)
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  updateSoccerField(soccerField: SoccerFieldModel) {
    return this.http.put(
      API_SERVER + ApiRoute.SOCCER_FIELDS_WITH_ID.replace(
        PATH_SOCCER_FIELD_ID,
        (soccerField.id || "").toLocaleString()
      ),
      soccerField,
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService),
        observe: "response"
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  deleteSoccerField(soccerFieldId) {
    return this.http.delete(
      API_SERVER + ApiRoute.SOCCER_FIELDS_WITH_ID.replace(
        PATH_SOCCER_FIELD_ID,
        (soccerFieldId || "").toLocaleString()
      ),
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService),
        observe: "response"
      }
    ).pipe(catchError(SoccerFieldService.errorHandler));
  }

  private static errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }
}
