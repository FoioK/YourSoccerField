export function compareTime(today: Date, data: Date): boolean {
  let todayData: Date = new Date(today);
  let dataSecond: Date = new Date(data);

  todayData.setHours(0, 0, 0, 0);
  dataSecond.setHours(0, 0, 0, 0);

  if (dataSecond.getTime() === todayData.getTime()) {
    todayData = new Date(today);
    dataSecond = new Date(data);
    if (dataSecond < todayData) {
      return true;
    }
  } else {
    return false;
  }
}
