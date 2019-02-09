import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {SoccerField} from '../../shared/models/soccer-field';
import {SoccerFieldService} from '../../core/http/soccer-field/soccer-field.service';
import {switchMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {Surface} from 'src/app/shared/models/surface';

@Component({
  selector: 'app-main-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [
    trigger('showHide', [
      state(
        'show',
        style({
          opacity: 1
        })
      ),
      state(
        'hide',
        style({
          opacity: 0
        })
      ),
      transition('show=>hide', [animate('0.5s')]),
      transition('hide=>show', [animate('0.5s')])
    ])
  ]
})
export class HomeComponent implements OnInit {
  filterShow = false;
  filterForm: FormGroup;
  surfaces: FormArray = this.formBuilder.array([]);
  addressGroup: FormGroup;
  exampleSoccerFieldList: Array<SoccerField>;
  promptSoccerFieldList: Array<SoccerField>;
  surfacesList: Array<Surface>;

  constructor(
    private formBuilder: FormBuilder,
    private soccerFieldService: SoccerFieldService,
  ) {

  }

  ngOnInit() {
    this.getExampleSoccerfields();
    this.getAllSurfaces();
    this.initAddressForm();
    this.buildFilterForm();
  }

  private getExampleSoccerfields() {
    this.soccerFieldService.getExampleTen().subscribe(result => {
      this.exampleSoccerFieldList = result;
    });
  }

  private getAllSurfaces() {
    this.soccerFieldService.getAllSurfaces().subscribe(result => {
      this.surfacesList = result;
    });
  }

  private initAddressForm() {
    this.addressGroup = this.formBuilder.group({
      address: ''
    });

    this.addressGroup
      .get('address')
      .valueChanges.pipe(
      switchMap(street =>
        street.toString().length > 0
          ? this.soccerFieldService.findByAddressContains(street)
          : of([])
      )
    )
      .subscribe(result => {
        this.promptSoccerFieldList = result;
      });
  }

  private buildFilterForm() {
    this.filterForm = this.formBuilder.group({
      surfaces: this.surfaces,
      paid: false,
      lighting: false,
      fenced: false,
      lockerRoom: false,
      widthMin: 0,
      widthMax: 100,
      lengthMin: 0,
      lengthMax: 100
    });

    this.filterForm.valueChanges.subscribe(value => {
      this.soccerFieldService.findByCustomCriteria(value).subscribe(
        result => {
          this.promptSoccerFieldList = result;
        },
        () => {
        },
        () => {
        }
      );
    });
  }

  private createNewControl(id: number) {
    return new FormControl(id);
  }

  private setSomething(id: number): void {
    const index: number = this.surfaces.value.findIndex(control => control === id);

    if (index === -1) {
      this.surfaces.push(this.createNewControl(id));
    } else {
      this.surfaces.removeAt(index);
    }
  }

  private getWidthMin(value: number): void {
    this.filterForm.controls['widthMin'].setValue(value);
  }

  private getWidthMax(value: number): void {
    this.filterForm.controls['widthMax'].setValue(value);
  }

  private getLengthMin(value: number): void {
    this.filterForm.controls['lengthMin'].setValue(value);
  }

  private getLengthMax(value: number): void {
    this.filterForm.controls['lengthMax'].setValue(value);
  }
}
