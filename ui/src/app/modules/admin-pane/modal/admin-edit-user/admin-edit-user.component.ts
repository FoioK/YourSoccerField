import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserService} from "../../../../service/user.service";
import {User} from "../../../../model/user";
import {SoccerField} from "../../../../model/soccer-field";
import {Configuration} from "../../../../service/configuration";

@Component({
  selector: 'app-admin-edit-user',
  templateUrl: './admin-edit-user.component.html',
  styleUrls: ['./admin-edit-user.component.css']
})
export class AdminEditUserComponent implements OnInit {

  form: FormGroup;

  user: User;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private dialogRef: MatDialogRef<AdminEditUserComponent>,
    private configuration: Configuration,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.user = data;
  }

  ngOnInit() {
    this.builtForm(this.user);
  }

  private builtForm(user: User) {
    this.form = this.formBuilder.group({
      email: [
        user.email,
        [
          Validators.required,
          Validators.maxLength(64),
          Validators.pattern(this.configuration.getEmailRegExp())
        ]
      ],
      firstName: [
        user.firstName,
        [
          Validators.required,
          Validators.maxLength(32),
        ]
      ],
      secondName: [
        user.secondName,
        [
          Validators.required,
          Validators.maxLength(32)
        ]
      ],
      nickname: [
        user.nickname,
        [
          Validators.required,
          Validators.maxLength(32)
        ]
      ],
      active: [
        user.active,
        Validators.required,
      ]
    })
  }

  private save() {
    this.dialogRef.close(this.prepareUser());
  }

  private prepareUser(): User {
    const editUser: User = this.form.value;
    this.user.email = editUser.email;
    this.user.firstName = editUser.firstName;
    this.user.secondName = editUser.secondName;
    this.user.nickname = editUser.nickname;
    this.user.active = editUser.active;

    return this.user;
  }

  private close() {
    this.dialogRef.close();
  }
}
