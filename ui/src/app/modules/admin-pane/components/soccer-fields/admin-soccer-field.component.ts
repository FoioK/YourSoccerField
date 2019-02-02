import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../service/soccer-field.service";
import {SoccerField} from "../../../../model/soccer-field";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {AdminEditSoccerFieldComponent} from "../../modal/admin-edit-soccer-field/admin-edit-soccer-field.component";

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
              private dialog: MatDialog
  ) {
    this.dialogConf = new MatDialogConfig();
    this.dialogConf.disableClose = true;
    this.dialogConf.autoFocus = true;
  }

  ngOnInit() {
    this.getAllSoccerField();
  }

  private getAllSoccerField() {
    return this.soccerFieldService.findAll()
      .subscribe(data => this.soccerFields = data);
  }

  editSoccerField(soccerField: SoccerField) {
    this.editUserDialog = this.dialog.open(
      AdminEditSoccerFieldComponent,
      {
        width: '60%',
        disableClose: true,
        autoFocus: true,
        data: soccerField
      });

    this.editUserDialog.afterClosed()
      .subscribe((result: SoccerField) =>
        result ? this.updateSoccerField(result) : undefined);
  }

  updateSoccerField(soccerField: SoccerField) {
    this.soccerFieldService.updateSoccerField(soccerField)
      .subscribe(result => {
        if (result.status == 201 || result.status == 204) {
          this.getAllSoccerField();
        }
      });
  }

}
