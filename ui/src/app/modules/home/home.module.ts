import {NgModule} from "@angular/core";
import {HomeComponent} from "./home.component";
import {SharedModule} from "../../shared/shared.module";
import {HomeRoutingModule} from "./home-routing.module";
import {MiniSoccerfieldComponent} from "./components/mini-socerfield/mini-socerfield.component";
import {MultirangeSliderComponent} from "./components/multirange-slider/multirange-slider.component";

@NgModule({
  declarations: [
    HomeComponent,
    MiniSoccerfieldComponent,
    MultirangeSliderComponent,
  ],
  imports: [
    SharedModule,
    HomeRoutingModule,
  ],
  providers: []
})
export class HomeModule {

}
