import { Component, OnInit } from "@angular/core";
import { trigger, state, style, animate, transition } from "@angular/animations";
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
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

  constructor(private formBuilder: FormBuilder) {}

  filterShow: boolean = false;

  filterForm: FormGroup;

  ngOnInit() {
    this.filterForm = this.formBuilder.group({
      surfaces : this.getSurfaces(),
      paid : [false],
      lighting : [false],
      fance : [false],
      lockerRoom : [false],
      width : this.getWidth(),
      length : this.getLength()
    });
  }

  getSurfaces(): FormGroup {
    return this.formBuilder.group({
      syntheticGrass : [false],
      rubber : [false],
      tartan : [false]
    });
  }

  getWidth(): FormGroup {
    return this.formBuilder.group({
      widthMin : [0],
      widthMax : [100],
    });
  }

  getLength(): FormGroup {
    return this.formBuilder.group({
      lengthMin : [0],
      lengthMax : [100]
    });
  }  
}
