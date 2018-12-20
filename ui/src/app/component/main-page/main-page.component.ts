import { Component, OnInit } from "@angular/core";
import {
  animate,
  state,
  style,
  transition,
  trigger
} from "@angular/animations";
import { FormBuilder, FormGroup } from "@angular/forms";
import { SoccerField } from "../../model/soccer-field";
import { SoccerFieldService } from "../../service/soccer-field.service";
import { switchMap } from "rxjs/operators";

@Component({
  selector: "app-main-page",
  templateUrl: "./main-page.component.html",
  styleUrls: ["./main-page.component.css"],
  animations: [
    trigger("showHide", [
      state(
        "show",
        style({
          opacity: 1
        })
      ),
      state(
        "hide",
        style({
          opacity: 0
        })
      ),
      transition("show=>hide", [animate("0.5s")]),
      transition("hide=>show", [animate("0.5s")])
    ])
  ]
})
export class MainPageComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private soccerFieldService: SoccerFieldService
  ) {}
  filterShow = false;
  filterForm: FormGroup;

  addressGroup: FormGroup;
  promptSoccerFieldList: Array<SoccerField>;

  ngOnInit() {
    this.initAddressForm();
    this.buildFilterForm();
  }

  private initAddressForm() {
    this.addressGroup = this.formBuilder.group({
      address: ""
    });

    this.addressGroup
      .get("address")
      .valueChanges.pipe(
        switchMap(street =>
          this.soccerFieldService.findByAddressContains(street)
        )
      )
      .subscribe(result => {
        this.promptSoccerFieldList = result;
        console.log(this.promptSoccerFieldList);
      });
  }

  private buildFilterForm() {
    this.filterForm = this.formBuilder.group({
      surfaces: this.getSurfaces(),
      paid: false,
      lighting: false,
      fenced: false,
      lockerRoom: false,
      width: this.getWidth(),
      length: this.getLength()
    });
  }

  private getSurfaces(): FormGroup {
    return this.formBuilder.group({
      syntheticGrass: false,
      rubber: false,
      tartan: false
    });
  }

  private getWidth(): FormGroup {
    return this.formBuilder.group({
      widthMin: 0,
      widthMax: 100
    });
  }

  private getLength(): FormGroup {
    return this.formBuilder.group({
      lengthMin: 0,
      lengthMax: 100
    });
  }

  private getWidthMin(value: number): void {
    this.filterForm.controls["width"].patchValue({ widthMin: value });
  }

  private getWidthMax(value: number): void {
    this.filterForm.controls["width"].patchValue({ widthMax: value });
  }

  private getLengthMin(value: number): void {
    this.filterForm.controls["length"].patchValue({ lengthMin: value });
  }

  private getLengthMax(value: number): void {
    this.filterForm.controls["length"].patchValue({ lengthMax: value });
  }
}
