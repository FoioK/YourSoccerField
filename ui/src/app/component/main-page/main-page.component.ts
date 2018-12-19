import { Component, OnInit } from "@angular/core";
import {
  animate,
  state,
  style,
  transition,
  trigger
} from "@angular/animations";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { SoccerField } from "../../model/SoccerField";
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
    this.getExampleSocerfields();
    this.initAddressForm();
    this.buildFilterForm();
  }

  private getExampleSocerfields() {
    this.soccerFieldService.getExampleTen().subscribe(result => {
      this.promptSoccerFieldList = result;
      console.log(this.filterForm.value);
    });
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
      surfaces: this.formBuilder.array(['','','']),
      paid: false,
      lighting: false,
      fenced: false,
      lockerRoom: false,
      widthMin: 0,
      widthMax: 100,
      lengthMin: 0,
      lengthMax: 100
    });
  }

  private setSurfaces(index: number, value: string): void {
    if (this.filterForm.controls["surfaces"].value[index] === "") {
      this.filterForm.controls["surfaces"].value[index] = value;
    } else {
      this.filterForm.controls["surfaces"].value[index] = "";
    }
  }

  private getWidthMin(value: number): void {
    this.filterForm.controls["widthMin"].setValue(value);
  }

  private getWidthMax(value: number): void {
    this.filterForm.controls["widthMax"].setValue(value);
  }

  private getLengthMin(value: number): void {
    this.filterForm.controls["lengthMin"].setValue(value);
  }

  private getLengthMax(value: number): void {
    this.filterForm.controls["lengthMax"].setValue(value);
  }
}
