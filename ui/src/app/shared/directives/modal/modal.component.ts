import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormBuilder, FormGroup} from "@angular/forms";
import {SoccerField} from "../../../model/soccer-field";
import {Address} from "../../../model/address";

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  form: FormGroup;
  addressForm: FormGroup;

  soccerField: SoccerField;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<ModalComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.soccerField = data.soccerField;
  }

  ngOnInit() {
    this.builtForm(this.soccerField);
    this.buildAddressForm(this.soccerField.address);
  }

  private builtForm(soccerField: SoccerField) {
    this.form = this.formBuilder.group({
      name: soccerField.name,
      address: this.addressForm,
      surface: soccerField.surface,
      width: soccerField.width,
      length: soccerField.length,
      price: soccerField.price
    })
  }

  private buildAddressForm(address: Address) {
    this.addressForm = this.formBuilder.group({
      city: address.city,
      street: address.street,
      apartmentNumber: address.apartmentNumber
    });
  }

  save() {
    this.dialogRef.close(this.form.value);
  }

  close() {
    this.dialogRef.close("CLOSE");
  }

}
