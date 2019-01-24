import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../service/soccer-field.service";
import {SoccerField} from "../../../../model/soccer-field";
import {ModalService} from "../../../../service/modal.service";

@Component({
  selector: 'app-admin-soccer-field',
  templateUrl: './admin-soccer-field.component.html',
  styleUrls: ['./admin-soccer-field.component.css']
})
export class AdminSoccerFieldComponent implements OnInit {

  public soccerFields = [];

  constructor(private soccerFieldService: SoccerFieldService,
              private modalService: ModalService) {
  }

  ngOnInit() {
    this.soccerFieldService.findAll()
      .subscribe(data => this.soccerFields = data);
  }

  editSoccerField(soccerField: SoccerField) {
    this.modalService.open('custom-modal-1');
  }
}
