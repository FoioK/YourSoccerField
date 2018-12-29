import { Component, OnInit } from "@angular/core";
import { AuthService } from "../../service/auth.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Configuration } from "../../service/configuration";
import { AppRoute } from "../../module/app-route";
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
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

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [
        "",
        [
          Validators.required,
          Validators.pattern(this.configuration.getEmailRegExp())
        ]
      ],
      password: ["", Validators.required]
    });
  }

  logIn() {
    this.authService.getAccessToken(
      this.loginForm.get("email").value,
      this.loginForm.get("password").value
    );

    this.userService.isLogged().subscribe(
      response => {
        if (response) {
          this.router.navigateByUrl(AppRoute.mainPage);
        } else {
          // console.log(response);
        }
      },
      err => {
        // console.log(err);
      }
    );
  }

  goToRegistration() {
    this.router.navigateByUrl(AppRoute.registration);
  }
}
