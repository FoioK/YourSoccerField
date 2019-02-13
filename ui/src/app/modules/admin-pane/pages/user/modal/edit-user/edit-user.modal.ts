import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserService} from "../../../../../../core/http/user/user.service";
import {User} from "../../../../../../shared/models/user";
import {EMAIL_REG_EXP} from "../../../../../../configs/configuration";

@Component({
  selector: 'app-admin-edit-user',
  templateUrl: './edit-user.modal.html',
  styleUrls: ['./edit-user.modal.css']
})
export class EditUserModal implements OnInit {

  form: FormGroup;

  user: User;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private dialogRef: MatDialogRef<EditUserModal>,
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
          Validators.pattern(EMAIL_REG_EXP)
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
