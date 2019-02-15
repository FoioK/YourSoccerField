import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {SoccerFieldModel} from "../../../../../../shared/models/soccer-field.model";
import {AddressModel} from "../../../../../../shared/models/address.model";
import {SoccerFieldService} from "../../../../../../core/http/soccer-field/soccer-field.service";
import {SurfaceModel} from "../../../../../../shared/models/surface.model";

@Component({
  selector: 'app-admin-edit-soccer-field',
  templateUrl: './edit-soccer-field.modal.html',
  styleUrls: ['./edit-soccer-field.modal.css']
})
export class EditSoccerFieldModal implements OnInit {

  private form: FormGroup;
  private addressForm: FormGroup;

  private readonly soccerField: SoccerFieldModel;
  private surfaceList: Array<SurfaceModel>;

  constructor(
    private formBuilder: FormBuilder,
    private soccerFieldService: SoccerFieldService,
    private dialogRef: MatDialogRef<EditSoccerFieldModal>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.soccerField = data;
  }

  ngOnInit() {
    this.builtForm(this.soccerField);
    this.buildAddressForm(this.soccerField.address);
    this.getAllSurfaces();
  }

  private save() {
    this.dialogRef.close(this.prepareSoccerField());
  }

  private close() {
    this.dialogRef.close();
  }

  private builtForm(soccerField: SoccerFieldModel) {
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

  private buildAddressForm(address: AddressModel) {
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

  private prepareSoccerField(): SoccerFieldModel {
    const address: AddressModel = this.addressForm.value;
    address.id = this.soccerField.address.id;

    const editSoccerField: SoccerFieldModel = this.form.value;
    this.soccerField.name = editSoccerField.name;
    this.soccerField.surface = editSoccerField.surface;
    this.soccerField.address = address;
    this.soccerField.width = editSoccerField.width;
    this.soccerField.length = editSoccerField.length;
    this.soccerField.price = editSoccerField.price;

    return this.soccerField;
  }

}
