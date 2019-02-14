import {NgModule} from "@angular/core";
import {HomeComponent} from "./home.component";
import {SharedModule} from "../../shared/shared.module";
import {HomeRoutingModule} from "./home-routing.module";
import {MiniSoccerFieldComponent} from "./components/mini-socerfield/mini-socerfield.component";
import {MultiRangeSliderComponent} from "./components/multirange-slider/multi-range-slider.component";

@NgModule({
  declarations: [
    HomeComponent,
    MiniSoccerFieldComponent,
    MultiRangeSliderComponent,
  ],
  imports: [
    SharedModule,
    HomeRoutingModule,
  ],
  providers: []
})
export class HomeModule {

}
