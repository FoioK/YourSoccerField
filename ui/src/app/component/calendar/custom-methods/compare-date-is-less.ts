export function compareDateIsLess(today: Date, data: Date): boolean {
  today.setHours(0, 0, 0, 0);
  data.setHours(0, 0, 0, 0);
  if (data < today) {
    return true;
  } else {
    return false;
  }
}
