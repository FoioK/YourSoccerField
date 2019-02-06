import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { passValidator } from '../../custom-validators/passValidator';
import { Configuration } from '../../service/configuration';
import { AppRoute } from '../../module/app-route';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private configuration: Configuration,
    private userService: UserService,
    private router: Router
  ) {}

  registrationForm: FormGroup;
  registrationErrorMsg: string;
  errorReason: string;

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      nickname: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      secondName: ['', [Validators.required]],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(this.configuration.getEmailRegExp())
        ]
      ],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required, passValidator]]
    });

    this.registrationForm.controls.password.valueChanges.subscribe(x =>
      this.registrationForm.controls.confirmPassword.updateValueAndValidity()
    );
  }

  getLoginRoute(): string {
    return '/' + AppRoute.LOGIN;
  }

  createUser() {
    this.userService.createUser(this.registrationForm.value).subscribe(
      response => {
        if (response.status === 201) {
          this.router.navigateByUrl(AppRoute.LOGIN);
        }
      },
      (error: HttpErrorResponse) => {
        if (error.error === 'DUPLICATE_USER_NICKNAME') {
          this.errorReason = 'nickname';
        }
        if (error.error === 'DUPLICATE_USER_EMAIL') {
          this.errorReason = 'email';
        }
        this.registrationErrorMsg = error.message;
      }
    );
  }
}
