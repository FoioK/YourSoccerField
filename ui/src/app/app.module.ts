import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {Configuration} from './configs/configuration';
import {ApiMapping} from './configs/api-mapping';
import {DatePipe} from '@angular/common';
import {CoreModule} from "./core/core.module";
import {SharedModule} from "./shared/shared.module";
import {AppRoutingModule} from "./app-routing.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    SharedModule,
    CoreModule,
    AppRoutingModule
  ],
  providers: [
    Configuration,
    ApiMapping,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
