import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject} from "rxjs";
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() {
  }

  private isLoggedSubject = new BehaviorSubject<Boolean>(false);

  isLogged(): Observable<Boolean> {
    return this.isLoggedSubject.asObservable();
  }

  setLogged(isLogged: Boolean) {
    this.isLoggedSubject.next(isLogged);
  }

  logOut() {
    localStorage.clear();
    this.setLogged(false);
  }
}
