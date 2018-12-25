import { Component, OnInit, Input } from '@angular/core';
import { SoccerField } from '../../model/soccer-field';

@Component({
  selector: 'app-mini-socerfield',
  templateUrl: './mini-socerfield.component.html',
  styleUrls: ['./mini-socerfield.component.css']
})
export class MiniSocerfieldComponent implements OnInit {

  @Input()
  private field : SoccerField;
  @Input()
  private even : boolean = false;

  constructor() { }

  ngOnInit() {
  }

}
