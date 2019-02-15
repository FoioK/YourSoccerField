import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'convertTime12To24Format'
  // only use with pipe | date:'dd.MM.yyyy h:mm:ss a'
})
export class ConvertTime12To24FormatPipe implements PipeTransform {
  transform(time: any): any {
    const data: string = time.toString();
    const date: string = time.split(' ')[0];
    const modifier: string = time.split(' ')[2];
    const time12: string = time.split(' ')[1];
    let hour = parseInt(time12.split(':')[0], 10);
    let min = time12.split(':')[1];
    if (min === '0') {
      min = '00';
    }
    if (modifier === 'PM' && hour !== 12) {
      hour += 12;
    }
    if (hour === 24) {
      return date + ' ' + '00' + ':' + min;
    } else {
      return date + ' ' + hour + ':' + min;
    }
  }
}
