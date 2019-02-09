import {NgModule} from '@angular/core';

import {AdminPanelComponent} from "./admin-panel.component";
import {SoccerFieldListComponent} from "./pages/soccer-field/soccer-field-list.component";
import {AdminPaneRoutingModule} from "./admin-pane-routing.module";
import {DataTableModule} from "angular-6-datatable";
import {MatDialogModule} from "@angular/material";
import {EditSoccerFieldModal} from "./pages/soccer-field/modal/edit-soccer-field/edit-soccer-field.modal";
import {UserListComponent} from './pages/user/user-list.component';
import {EditUserModal} from './pages/user/modal/edit-user/edit-user.modal';
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    AdminPanelComponent,
    SoccerFieldListComponent,
    EditSoccerFieldModal,
    UserListComponent,
    EditUserModal
  ],
  imports: [
    SharedModule,
    AdminPaneRoutingModule,

    DataTableModule,
    MatDialogModule
  ],
  providers: [],
  entryComponents: [
    EditSoccerFieldModal,
    EditUserModal
  ]
})
export class AdminPaneModule {
}
