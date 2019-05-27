package com.pk.ysf.api.util

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
val dateTimeToString: (LocalDateTime) -> String = { date -> date.format(DATE_FORMAT) }
val stringToDateTime: (String) -> LocalDateTime = { string -> LocalDateTime.parse(string, DATE_FORMAT) }


val TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm")
val timeToString: (LocalTime) -> String = { time -> time.format(TIME_FORMAT) }
val stringToTime: (String) -> LocalTime = { string -> LocalTime.parse(string, TIME_FORMAT) }
