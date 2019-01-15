import {NgModule} from '@angular/core';

import {AdminPaneComponent} from "./components/admin-pane.component";
import {AdminSoccerFieldComponent} from "./components/soccer-fields/admin-soccer-field.component";
import {HttpClientModule} from "@angular/common/http";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";

@NgModule({
  declarations: [
    AdminPaneComponent,
    AdminSoccerFieldComponent,
  ],
  imports: [
    HttpClientModule,
    AdminPaneRoutingModule,
  ],
  providers: []
})
export class AdminPaneModule {
}
