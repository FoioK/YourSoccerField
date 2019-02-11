import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {Configuration} from './configs/configuration';
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
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
