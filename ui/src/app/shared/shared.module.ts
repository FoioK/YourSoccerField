import {NgModule} from "@angular/core";
import {NavbarComponent} from "../core/header/navbar.component";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {FooterComponent} from "../core/footer/footer.component";

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  exports: [
    NavbarComponent,
    FooterComponent,

    CommonModule,
    ReactiveFormsModule,
  ],
  providers: [],
})
export class SharedModule {

}