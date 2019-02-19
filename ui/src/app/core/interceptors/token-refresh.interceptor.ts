import { Injectable } from '@angular/core';
import { HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { HttpInterceptor } from "@angular/common/http/src/interceptor";
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { HttpEvent } from '@angular/common/http/src/response';
import { catchError, switchMap, filter, take } from "rxjs/operators";
import { AuthenticationService } from "../authentication/authentication.service";
import { SessionService } from '../services/session.service';
import { TokenModel } from '../../shared/models/token.model';

@Injectable()
export class TokenRefreshInterceptor implements HttpInterceptor {

  private isRefreshingToken: boolean = false;
  private tokenSubject: BehaviorSubject<TokenModel> = new BehaviorSubject<TokenModel>(null);

  constructor(private authService: AuthenticationService,
              private seesionService: SessionService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(error => {
      if(
        error instanceof HttpErrorResponse
        && error.status === 401
        && this.seesionService.getToken()!== null
      ){
        return this.handleError401(req,next);
      }
      else{
        return throwError(error);
      }
    }));
  }

  private addHeaderAuthorization(req: HttpRequest<any>, token: TokenModel): HttpRequest<any>{
    return req.clone(
      { setHeaders: { 'Authorization': token.token_type + ' ' + token.access_token }}
    );
  }

  private handleError401(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(!this.isRefreshingToken){
      this.isRefreshingToken = true;
      this.tokenSubject.next(null);

      return this.authService.refreshAccessToken(this.seesionService.getToken().refresh_token)
      .pipe(switchMap(
      (token: TokenModel) =>  {
        this.isRefreshingToken = false;
        this.tokenSubject.next(token);
          return next.handle(this.addHeaderAuthorization(req,token));
      }));
    }else{
      return this.tokenSubject.pipe(
        filter(token => token !== null),
        take(1),
        switchMap((token) => next.handle(this.addHeaderAuthorization(req,token)))
        );
    }
  }
}