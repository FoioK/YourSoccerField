import {NgModule} from "@angular/core";
import {NavBarComponent} from "../core/header/nav-bar.component";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {FooterComponent} from "../core/footer/footer.component";
import {PageNotFoundComponent} from "../core/page-not-found/page-not-found.component";

@NgModule({
  declarations: [
    NavBarComponent,
    FooterComponent,
    PageNotFoundComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  exports: [
    NavBarComponent,
    FooterComponent,

    CommonModule,
    ReactiveFormsModule,
  ],
  providers: [],
})
export class SharedModule {

}
