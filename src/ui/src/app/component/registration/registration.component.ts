import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { }

  registrationForm : FormGroup;

  private emailReqExp: RegExp =
  RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);


  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
     nickname: ['', [Validators.required]],
     firstName: ['', [Validators.required]],
     secondName: ['', []],
     email: ['', [Validators.required, Validators.pattern(this.emailReqExp)]],
     password: ['', [Validators.required]],
    })
  }

}
