import {NgModule} from '@angular/core';

import {AdminPaneComponent} from "./components/admin-pane.component";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {HttpClientModule} from "@angular/common/http";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";
import {DataTableModule} from "angular-6-datatable";
import {CommonModule} from "@angular/common";
import {MatDialogModule} from "@angular/material";
import {ModalComponent} from "../../shared/directives/modal/modal.component";

@NgModule({
  declarations: [
    AdminPaneComponent,
    AdminSoccerFieldComponent,
    ModalComponent
  ],
  imports: [
    HttpClientModule,
    AdminPaneRoutingModule,
    CommonModule,
    DataTableModule,
    MatDialogModule
  ],
  providers: [],
  entryComponents: [
    ModalComponent
  ]
})
export class AdminPaneModule {
}
