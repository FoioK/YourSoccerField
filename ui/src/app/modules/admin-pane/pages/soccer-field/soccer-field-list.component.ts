import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../core/http/soccer-field/soccer-field.service";
import {SoccerFieldModel} from "../../../../shared/models/soccer-field.model";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {EditSoccerFieldModal} from "./modal/edit-soccer-field/edit-soccer-field.modal";

@Component({
  selector: 'app-admin-soccer-field',
  templateUrl: './soccer-field-list.component.html',
  styleUrls: ['./soccer-field-list.component.css']
})
export class SoccerFieldListComponent implements OnInit {

  public soccerFields = [];

  private dialogConf: MatDialogConfig;
  private editSoccerFieldDialog;

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

  editSoccerField(soccerField: SoccerFieldModel) {
    this.editSoccerFieldDialog = this.dialog.open(
      EditSoccerFieldModal,
      {
        width: '60%',
        disableClose: true,
        autoFocus: true,
        data: soccerField
      });

    this.editSoccerFieldDialog.afterClosed()
      .subscribe((result: SoccerFieldModel) =>
        result ? this.updateSoccerField(result) : undefined);
  }

  private updateSoccerField(soccerField: SoccerFieldModel) {
    this.soccerFieldService.updateSoccerField(soccerField)
      .subscribe(result => {
        if (result.status == 201 || result.status == 204) {
          this.getAllSoccerField();
        }
      });
  }

  deleteSoccerField(soccerFieldId) {
    this.soccerFieldService.deleteSoccerField(soccerFieldId)
      .subscribe(result => {
        if (result.status == 200 || result.status == 204) {
          this.getAllSoccerField();
        }
      })
  }

}
