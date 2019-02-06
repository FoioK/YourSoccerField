import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Configuration } from '../../service/configuration';
import { AppRoute } from '../../module/app-route';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private configuration: Configuration,
    private userService: UserService,
    private router: Router
  ) {}

  loginForm: FormGroup;
  loginErrorMsg: string;
  errorStatus: number;

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(this.configuration.getEmailRegExp())
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
          this.userService.isLogged().subscribe(response => {
            if (response) {
              this.router.navigateByUrl(AppRoute.MAIN_PAGE);
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
    this.router.navigateByUrl(AppRoute.REGISTRATION);
  }
}
