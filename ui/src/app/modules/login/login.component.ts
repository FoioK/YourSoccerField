import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/authentication/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EMAIL_REG_EXP} from '../../configs/configuration';
import {AppRoute} from '../../app.route';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {SessionService} from "../../core/services/session.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginErrorMsg: string;
  errorStatus: number;

  constructor(
    private authService: AuthenticationService,
    private formBuilder: FormBuilder,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(EMAIL_REG_EXP)
        ]
      ],
      password: ['', Validators.required]
    });
  }

  logIn() {
    this.authService
      .getAccessToken(
        this.loginForm.get('email').value,
        this.loginForm.get('password').value
      )
      .subscribe(
        () => {
          this.sessionService.isLogged().subscribe(response => {
            if (response) {
              this.router.navigateByUrl(AppRoute.HOME);
            }
          });
          this.errorStatus = -1;
          this.loginErrorMsg = '';
        },
        (error: HttpErrorResponse) => {
          if (error.status === 400) {
            this.loginErrorMsg =
              'The password that you have entered is incorrect';
            this.errorStatus = error.status;
          }
          if (error.status === 401) {
            this.loginErrorMsg = 'The email that you have entered is incorrect';
            this.errorStatus = error.status;
          }
        }
      );
  }

  goToRegistration() {
    this.router.navigateByUrl(AppRoute.LOGIN + '/' + AppRoute.REGISTRATION);
  }
}
