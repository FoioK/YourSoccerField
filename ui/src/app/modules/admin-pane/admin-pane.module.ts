import {NgModule} from '@angular/core';

import {AdminPaneComponent} from "./components/admin-pane.component";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {HttpClientModule} from "@angular/common/http";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";
import {DataTableModule} from "angular-6-datatable";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    AdminPaneComponent,
    AdminSoccerFieldComponent,
  ],
  imports: [
    HttpClientModule,
    AdminPaneRoutingModule,
    CommonModule,
    DataTableModule,
  ],
  providers: []
})
export class AdminPaneModule {
}
