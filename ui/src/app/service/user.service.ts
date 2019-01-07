import { Injectable } from "@angular/core";
import { Observable, BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UserService {
  constructor() {}

  private isLoggedSubject = new BehaviorSubject<boolean>(false);

  isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  setLogged(isLogged: boolean) {
    this.isLoggedSubject.next(isLogged);
  }

  logOut() {
    localStorage.removeItem('token');
    this.setLogged(false);
  }
}
