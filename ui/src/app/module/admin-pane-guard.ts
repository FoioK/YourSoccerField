import {CanActivate, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {UserService} from "../service/user.service";
import {Injectable} from "@angular/core";
import {catchError, map} from "rxjs/operators";
import {AppRoute} from "./app-route";

@Injectable({
  providedIn: 'root'
})
export class AdminPaneGuard implements CanActivate {

  constructor(
    private userService: UserService,
    private router: Router
  ) {

  }

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    return this.userService.adminPaneAuthenticate()
      .pipe(
        map(
          response => {
            if (response) {
              return true;
            }
            this.router.navigateByUrl(AppRoute.MAIN_PAGE);

            return false;
          }
        ),
        catchError(this.handleError.bind(this)));
  }

  private handleError() {
    this.router.navigateByUrl(AppRoute.MAIN_PAGE);
  }

}
