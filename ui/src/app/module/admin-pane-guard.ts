import {CanActivate} from '@angular/router';
import {Observable} from 'rxjs';
import {UserService} from "../service/user.service";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AdminPaneGuard implements CanActivate {

  constructor(
    private userService: UserService
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

            return false;
          }
        ));
  }

}
