import {Component, Input, OnInit} from '@angular/core';
import {SoccerField} from '../../../../shared/models/soccer-field';
import {Router} from '@angular/router';
import {AppRoute} from '../../../../configs/app-route';

@Component({
  selector: 'app-mini-socerfield',
  templateUrl: './mini-socerfield.component.html',
  styleUrls: ['./mini-socerfield.component.css']
})
export class MiniSoccerfieldComponent implements OnInit {
  @Input()
  field: SoccerField;
  @Input()
  private even: boolean = false;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  private book(data: SoccerField) {
    this.router.navigate([AppRoute.RESERVATION, data.id]);
  }
}