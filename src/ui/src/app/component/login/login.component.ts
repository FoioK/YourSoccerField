import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder) {
  }

  loginForm: FormGroup;

  private emailReqExp: RegExp =
    RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [
        '', [
          Validators.required,
          Validators.pattern(this.emailReqExp)
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
}
