import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../service/user.service";
import {MatDialog, MatDialogConfig} from "@angular/material";

@Component({
  selector: 'app-admin-user',
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css']
})
export class AdminUserComponent implements OnInit {

  public users = [];

  private dialogConf: MatDialogConfig;
  private editUserFialog;

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
    // return this.userService.findAll()
    //   .subscribe(data => this.users = data);
  }

}
