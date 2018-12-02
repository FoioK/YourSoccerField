import { Component, OnInit } from "@angular/core";
import { trigger, state, style, animate, transition } from "@angular/animations";
@Component({
  selector: "app-main-page",
  templateUrl: "./main-page.component.html",
  styleUrls: ["./main-page.component.css"],
  animations: [
    trigger('showHide', [
      state('show', style({
        opacity: 1,
      })),
      state('hide', style({
        opacity: 0,
      })),
      transition('show=>hide', [
        animate('0.5s')
      ]),
      transition('hide=>show', [
        animate('0.5s')
      ])
    ])
  ]
})
export class MainPageComponent implements OnInit {
  filterShow: boolean = false;
  maxWidth: number = 100;
  minWidth: number = 0;
  maxLength: number = 100;
  minLength: number = 0;

  constructor() {}

  ngOnInit() {}
}
