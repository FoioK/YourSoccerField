import {
  Component,
  OnInit,
  ElementRef,
  Renderer2,
  Output,
  EventEmitter
} from '@angular/core';

@Component({
  selector: 'app-multirange-slider',
  templateUrl: './multirange-slider.component.html',
  styleUrls: ['./multirange-slider.component.css']
})
export class MultirangeSliderComponent implements OnInit {
  private slider;
  private min;
  private max;
  private range;

  @Output()
  emitMin = new EventEmitter<number>();
  @Output()
  emitMax = new EventEmitter<number>();

  constructor(private el: ElementRef, private r2: Renderer2) {}

  ngOnInit() {
    this.slider = this.el.nativeElement.querySelector('.slider');
    this.min = this.slider.querySelector('.input-range.min');
    this.max = this.slider.querySelector('.input-range.max');
    this.range = this.slider.querySelector('.range');
  }

  private fillMin(): void {
    this.min.value = Math.min(this.min.value, this.max.value - 1);

    const valueMin =
      (100 / (parseInt(this.min.max, 10) - parseInt(this.min.min, 10))) *
        parseInt(this.min.value, 10) -
      (100 / (parseInt(this.min.max, 10) - parseInt(this.min.min, 10))) *
        parseInt(this.min.min, 10);

    const inputRangeLeft = this.slider.querySelector('.inverse-left');

    const thumbLeft = this.slider.querySelector('.thumb.thumb-left');

    this.range.style.left = valueMin + '%';

    inputRangeLeft.style.width = valueMin + '%';

    thumbLeft.style.left = valueMin + '%';

    this.emitMin.emit(this.min.value);
  }

  private fillMax(): void {
    this.max.value = Math.max(this.max.value, this.min.value - -1);

    const valueMax =
      (100 / (parseInt(this.max.max, 10) - parseInt(this.max.min, 10))) *
        parseInt(this.max.value, 10) -
      (100 / (parseInt(this.max.max, 10) - parseInt(this.max.min, 10))) *
        parseInt(this.max.min, 10);

    const inputRangeRight = this.slider.querySelector('.inverse-right');

    const thumbRight = this.slider.querySelector('.thumb.thumb-right');

    this.range.style.right = 100 - valueMax + '%';

    inputRangeRight.style.width = 100 - valueMax + '%';

    thumbRight.style.left = valueMax + '%';

    this.emitMax.emit(this.max.value);
  }
}
