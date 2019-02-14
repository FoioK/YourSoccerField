import {Component, Input, OnInit} from '@angular/core';
import {SoccerFieldModel} from '../../../../shared/models/soccer-field.model';
import {Router} from '@angular/router';
import {AppRoute} from '../../../../app.route';

@Component({
  selector: 'app-mini-socerfield',
  templateUrl: './mini-socerfield.component.html',
  styleUrls: ['./mini-socerfield.component.css']
})
export class MiniSoccerfieldComponent implements OnInit {
  @Input()
  field: SoccerFieldModel;
  @Input()
  private even: boolean = false;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  private book(data: SoccerFieldModel) {
    this.router.navigate([AppRoute.BOOKING, data.id]);
  }
}
