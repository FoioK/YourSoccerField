import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Configuration } from './configuration';
import { User } from '../model/user';
import { ApiMapping } from './api-mapping';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  constructor(
    private http: HttpClient,
    private configuration: Configuration,
    private apiMapping: ApiMapping
  ) {}

  private errorHandler(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse.error);
  }

  createUser(user: User): Observable<HttpResponse<User>> {
    return this.http
      .post<User>(
        this.configuration.apiServer + this.apiMapping.user_create,
        user,
        {
          headers: Configuration.getJSONContentType(),
          observe: 'response'
        }
      )
      .pipe(catchError(this.errorHandler));
  }
}
