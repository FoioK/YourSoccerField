import {NgModule} from '@angular/core';

import {AdminPaneComponent} from "./components/admin-pane.component";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {HttpClientModule} from "@angular/common/http";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";
import {DataTableModule} from "angular-6-datatable";
import {CommonModule} from "@angular/common";
import {MatDialogModule} from "@angular/material";
import {AdminEditSoccerFieldComponent} from './modal/admin-edit-soccer-field/admin-edit-soccer-field.component';

@NgModule({
  declarations: [
    AdminPaneComponent,
    AdminSoccerFieldComponent,
    AdminEditSoccerFieldComponent
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
    AdminEditSoccerFieldComponent
  ]
})
export class AdminPaneModule {
}
