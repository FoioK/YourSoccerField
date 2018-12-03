import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() {
  }

  private isLoggedSubject = new Subject<Boolean>();

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
