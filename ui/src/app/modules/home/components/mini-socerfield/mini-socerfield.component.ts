import {Component, Input} from '@angular/core';
import {SoccerFieldModel} from '../../../../shared/models/soccer-field.model';
import {Router} from '@angular/router';
import {AppRoute} from '../../../../app.route';

@Component({
  selector: 'app-mini-socerfield',
  templateUrl: './mini-socerfield.component.html',
  styleUrls: ['./mini-socerfield.component.css']
})
export class MiniSoccerFieldComponent {

  @Input()
  private field: SoccerFieldModel;

  @Input()
  private even: boolean = false;

  constructor(private router: Router) {
  }

  private book(data: SoccerFieldModel) {
    this.router.navigate([AppRoute.BOOKING, data.id]);
  }

}
