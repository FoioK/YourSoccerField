import {NgModule} from '@angular/core';

import {AdminPaneComponent} from "./components/admin-pane.component";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";
import {DataTableModule} from "angular-6-datatable";
import {CommonModule} from "@angular/common";
import {MatDialogModule} from "@angular/material";
import {AdminEditSoccerFieldComponent} from "./modal/admin-edit-soccer-field/admin-edit-soccer-field.component";
import {ReactiveFormsModule} from "@angular/forms";
import { AdminUserComponent } from './components/admin-user/admin-user.component';

@NgModule({
  declarations: [
    AdminPaneComponent,
    AdminSoccerFieldComponent,
    AdminEditSoccerFieldComponent,
    AdminUserComponent
  ],
  imports: [
    ReactiveFormsModule,
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
