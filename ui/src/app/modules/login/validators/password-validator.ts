import {AbstractControl} from '@angular/forms';

export function passwordValidator(control: AbstractControl) {
  if (control && (control.value !== null && control.value !== undefined)) {
    const confirmValue = control.value;

    const passwordControl = control.root.get('password');
    if (passwordControl) {
      if (passwordControl.value !== confirmValue) {
        return {
          isError: true
        };
      }
    }
  }
  return null;
}
