import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {SoccerField} from "../../../../model/soccer-field";
import {Address} from "../../../../model/address";
import {SoccerFieldService} from "../../../../service/soccer-field.service";
import {Surface} from "../../../../model/surface";

@Component({
  selector: 'app-admin-edit-soccer-field',
  templateUrl: './admin-edit-soccer-field.component.html',
  styleUrls: ['./admin-edit-soccer-field.component.css']
})
export class AdminEditSoccerFieldComponent implements OnInit {

  form: FormGroup;
  addressForm: FormGroup;

  soccerField: SoccerField;
  surfaceList: Array<Surface>;

  constructor(
    private formBuilder: FormBuilder,
    private soccerFieldService: SoccerFieldService,
    private dialogRef: MatDialogRef<AdminEditSoccerFieldComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.soccerField = data;
  }

  ngOnInit() {
    this.builtForm(this.soccerField);
    this.buildAddressForm(this.soccerField.address);
    this.getAllSurfaces();
  }

  private builtForm(soccerField: SoccerField) {
    this.form = this.formBuilder.group({
      name: [
        soccerField.name,
        [
          Validators.required,
          Validators.maxLength(64)
        ]
      ],
      address: this.addressForm,
      surface: [
        soccerField.surface,
        Validators.required
      ],
      width: [
        soccerField.width,
        [
          Validators.required,
          Validators.pattern("^[1-9][0-9]*$"),
          Validators.maxLength(3)
        ]
      ],
      length: [
        soccerField.length,
        [
          Validators.required,
          Validators.pattern("^[1-9][0-9]*$"),
          Validators.maxLength(3)
        ]
      ],
      price: [
        soccerField.price,
        [
          Validators.required,
          Validators.pattern("^(0|[1-9][0-9]*)(\\.[0-9]{2,2})?$")
        ]
      ]
    })
  }

  private buildAddressForm(address: Address) {
    this.addressForm = this.formBuilder.group({
      city: [
        address.city,
        [
          Validators.required,
          Validators.maxLength(64)
        ]
      ],
      street: [
        address.street,
        [
          Validators.required,
          Validators.maxLength(64)
        ]
      ],
      apartmentNumber: [
        address.apartmentNumber,
        [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
          Validators.maxLength(32)
        ]
      ]
    });
  }

  private getAllSurfaces() {
    this.soccerFieldService.getAllSurfaces().subscribe(result => {
      this.surfaceList = result;
    });
  }

  save() {
    this.dialogRef.close(this.prepareSoccerField());
  }

  private prepareSoccerField(): SoccerField {
    const address: Address = this.addressForm.value;
    address.id = this.soccerField.address.id;

    const editSoccerField: SoccerField = this.form.value;
    this.soccerField.name = editSoccerField.name;
    this.soccerField.surface = editSoccerField.surface;
    this.soccerField.address = address;
    this.soccerField.width = editSoccerField.width;
    this.soccerField.length = editSoccerField.length;
    this.soccerField.price = editSoccerField.price;

    return this.soccerField;
  }

  close() {
    this.dialogRef.close();
  }

}
