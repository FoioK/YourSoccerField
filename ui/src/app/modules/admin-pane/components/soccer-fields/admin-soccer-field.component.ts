import {Component, OnInit} from '@angular/core';
import {SoccerFieldService} from "../../../../service/soccer-field.service";

@Component({
  selector: 'app-admin-soccer-field',
  templateUrl: './admin-soccer-field.component.html',
  styleUrls: ['./admin-soccer-field.component.css']
})
export class AdminSoccerFieldComponent implements OnInit {

  public soccerFields = [];

  constructor(private soccerFieldService: SoccerFieldService) {
  }

  ngOnInit() {
    this.soccerFieldService.findAll()
      .subscribe(data => this.soccerFields = data);
  }

}
