import {Component} from '@angular/core';
import { Router } from '@angular/router';
import { AppRoute } from '../../app.route';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent {
  constructor(private router: Router){}

  private goToHome() {
    this.router.navigateByUrl("/" + AppRoute.HOME);
  }
}
