import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-internal-server-error',
  templateUrl: './internal-server-error.modal.html',
  styleUrls: ['./internal-server-error.modal.css']
})
export class InternalServerErrorModal implements OnInit {

  constructor() { }

  ngOnInit() {

  }

  private reloadPage(){
    location.reload();
  }

}
