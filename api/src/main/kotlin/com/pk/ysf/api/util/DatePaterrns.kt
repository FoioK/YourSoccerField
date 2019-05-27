package com.pk.ysf.api.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
const val SHORT_DATE_PATTERN = "yyyy-MM-dd HH:mm"

val TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm")
val timeToString: (LocalTime) -> String = { time -> time.format(TIME_FORMAT) }
