import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../service/soccer-field.service";
import {SoccerField} from "../../../../model/soccer-field";
import {ModalService} from "../../../../service/modal.service";
import {AdminPaneComponent} from "../admin-pane.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-admin-soccer-field',
  templateUrl: './admin-soccer-field.component.html',
  styleUrls: ['./admin-soccer-field.component.css']
})
export class AdminSoccerFieldComponent implements OnInit {

  public soccerFields = [];

  constructor(private soccerFieldService: SoccerFieldService,
              private modalService: ModalService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.soccerFieldService.findAll()
      .subscribe(data => this.soccerFields = data);
  }

  editSoccerField(soccerField: SoccerField) {
    let dialogRef = this.dialog.open(AdminPaneComponent, {
      height: '400px',
      width: '600px',
    });
    // this.modalService.open('edit-user-modal');
  }

  closeModal() {
    // this.modalService.close('edit-user-modal')
  }
}
