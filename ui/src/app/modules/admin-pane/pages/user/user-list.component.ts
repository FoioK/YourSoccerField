import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/http/user/user.service";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {User} from "../../../../shared/models/user";
import {EditUserModal} from "./modal/edit-user/edit-user.modal";

@Component({
  selector: 'app-admin-user',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  public users = [];

  private dialogConf: MatDialogConfig;
  private editUserDialog;

  constructor(
    private userService: UserService,
    private dialog: MatDialog
  ) {
    this.dialogConf = new MatDialogConfig();
    this.dialogConf.disableClose = true;
    this.dialogConf.autoFocus = true;
  }

  ngOnInit() {
    this.getAllUser();
  }

  private getAllUser() {
    return this.userService.findAll()
      .subscribe(data => this.users = data);
  }

  private editUser(user: User) {
    this.editUserDialog = this.dialog.open(
      EditUserModal,
      {
        width: '60%',
        disableClose: true,
        autoFocus: true,
        data: user
      });

    this.editUserDialog.afterClosed()
      .subscribe((result: User) =>
        result ? this.updateUser(result) : undefined);
  }

  private updateUser(user: User) {
    this.userService.updateUser(user)
      .subscribe(result => {
        if (result.status == 201 || result.status == 204) {
          this.getAllUser();
        }
      });
  }

  private deleteUser(userId) {
    this.userService.deleteUser(userId)
      .subscribe(result => {
        if (result.status == 200 || result.status == 204) {
          this.getAllUser();
        }
      })
  }
}