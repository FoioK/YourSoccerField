export function addTimeToDate(time: string, date: Date): Date {
  const result = new Date(date);
  const [hours, minutes] = time.split(':');

  result.setHours(
    result.getHours() + parseInt(hours, 10),
    result.getMinutes() + parseInt(minutes, 10)
  );

  return result;
}
