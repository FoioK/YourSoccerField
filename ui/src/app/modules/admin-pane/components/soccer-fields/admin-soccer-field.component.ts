import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../service/soccer-field.service";
import {SoccerField} from "../../../../model/soccer-field";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {ModalComponent} from "../../../../shared/directives/modal/modal.component";

@Component({
  selector: 'app-admin-soccer-field',
  templateUrl: './admin-soccer-field.component.html',
  styleUrls: ['./admin-soccer-field.component.css']
})
export class AdminSoccerFieldComponent implements OnInit {

  public soccerFields = [];

  private dialogConf: MatDialogConfig;
  private editUserDialog;

  constructor(private soccerFieldService: SoccerFieldService,
              private dialog: MatDialog) {
    this.dialogConf = new MatDialogConfig();
    this.dialogConf.disableClose = true;
    this.dialogConf.autoFocus = true;
  }

  ngOnInit() {
    this.soccerFieldService.findAll()
      .subscribe(data => this.soccerFields = data);
  }

  editSoccerField(soccerField: SoccerField) {
    this.editUserDialog = this.dialog.open(ModalComponent, {
      height: '400px',
      width: '600px',
      disableClose: true,
      autoFocus: true,
      data: soccerField
    });

    this.editUserDialog.afterClosed()
      .subscribe(result => {
        console.log(result)
      });
  }

}
