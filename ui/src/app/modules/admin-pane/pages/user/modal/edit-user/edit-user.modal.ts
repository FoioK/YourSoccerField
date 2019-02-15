import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserService} from "../../../../../../core/http/user/user.service";
import {UserModel} from "../../../../../../shared/models/user.model";
import {EMAIL_REG_EXP} from "../../../../../../configs/configuration";

@Component({
  selector: 'app-admin-edit-user',
  templateUrl: './edit-user.modal.html',
  styleUrls: ['./edit-user.modal.css']
})
export class EditUserModal implements OnInit {

  private form: FormGroup;

  private readonly user: UserModel;

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

  private builtForm(user: UserModel) {
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

  private prepareUser(): UserModel {
    const editUser: UserModel = this.form.value;
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
