import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {API_SERVER} from '../../../configs/configuration';
import {UserModel} from "../../../shared/models/user.model";
import {catchError} from "rxjs/operators";
import {HeaderService} from "../../services/header.service";
import {ApiRoute, PATH_USER_ID} from "../api.route";
import {SessionService} from "../../services/session.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private sessionService: SessionService
  ) {

  }

  adminPaneAuthenticate(): Observable<boolean> {
    return this.http.get<boolean>(
      API_SERVER + ApiRoute.USERS_ADMIN_AUTHENTICATE,
      {headers: HeaderService.tokenWithoutContentType(this.sessionService)}
    );
  }

  createUser(user: UserModel): Observable<HttpResponse<UserModel>> {
    return this.http
      .post<UserModel>(
        API_SERVER + ApiRoute.USERS,
        user,
        {
          headers: HeaderService.JSONContentType(),
          observe: 'response'
        }
      ).pipe(catchError(UserService.errorHandler));
  }

  findAll(): Observable<Array<UserModel>> {
    return this.http.get<Array<UserModel>>(
      API_SERVER + ApiRoute.USERS,
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService)
      }
    ).pipe(catchError(UserService.errorHandler));
  }

  updateUser(user: UserModel) {
    return this.http.put(
      API_SERVER + ApiRoute.USERS_WITH_ID.replace(PATH_USER_ID, (user.id || "").toLocaleString()),
      user,
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService),
        observe: "response"
      }
    ).pipe(catchError(UserService.errorHandler));
  }

  deleteUser(userId) {
    return this.http.delete(
      API_SERVER + ApiRoute.USERS_WITH_ID.replace(PATH_USER_ID, (userId.id || "").toLocaleString()),
      {
        headers: HeaderService.JSONContentTypeWithToken(this.sessionService),
        observe: "response"
      }
    ).pipe(catchError(UserService.errorHandler));
  }

  private static errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse.error);
  }
}
