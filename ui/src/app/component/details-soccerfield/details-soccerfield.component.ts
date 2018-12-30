import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-details-soccerfield',
  templateUrl: './details-soccerfield.component.html',
  styleUrls: ['./details-soccerfield.component.css']
})
export class DetailsSoccerfieldComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    console.log(this.route.snapshot.paramMap.get('id'));
  }

}
