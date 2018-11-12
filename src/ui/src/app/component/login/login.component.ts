import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Configuration} from "../../service/configuration";
import {AppRoute} from "../../module/app-route";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private configuration: Configuration) {
  }

  loginForm: FormGroup;

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [
        '', [
          Validators.required,
          Validators.pattern(this.configuration.getEmailRegExp())
        ]
      ],
      password: ['', Validators.required,]
    })
  }

  logIn() {
    this.authService.getAccessToken(
      this.loginForm.get('email').value,
      this.loginForm.get('password').value
    );
  }

  getRegisterRoute(): string {
    return '/' + AppRoute.registration;
  }
}
