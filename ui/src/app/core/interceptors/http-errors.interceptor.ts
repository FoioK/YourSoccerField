import { Injectable } from '@angular/core';
import { HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { HttpInterceptor } from "@angular/common/http/src/interceptor";
import { Observable, throwError } from 'rxjs';
import { HttpEvent } from '@angular/common/http/src/response';
import { catchError } from "rxjs/operators";
import { InternalServerErrorModal } from '../modal/internal-server-error/internal-server-error.modal';
import { MatDialogConfig, MatDialog } from '@angular/material';

@Injectable()
export class HttpErrorsInterceptor implements HttpInterceptor {

  private dialogConf: MatDialogConfig;
  private internalServerErrorModal;

  constructor(private dialog: MatDialog) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(error => {
      if(
        error instanceof HttpErrorResponse
        && error.status === 500
      ){
        this.handleError500(error);
        return throwError(error);
      }
      else{
        return throwError(error);
      }
    }));
  }

  private handleError500(error: HttpErrorResponse){
    this.dialogConf = new MatDialogConfig();
    this.dialogConf.autoFocus = true;
    this.internalServerErrorModal = this.dialog.open(
      InternalServerErrorModal,
      {
        autoFocus: true,
      }
    );
  }
}
