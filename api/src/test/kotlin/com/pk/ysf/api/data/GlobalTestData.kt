package com.pk.ysf.api.data

import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val IS_PAYED = true

const val SOCCER_FIELD_ID = 1L
const val BOOKING_ID = 3L
const val USER_CODE = 1L
const val OPEN_HOUR_ID = 4L
const val SURFACE_ID = 5L

const val START_DATE_STRING = "2019-05-05 11:00"
val START_DATE: LocalDateTime = LocalDateTime.parse(
        START_DATE_STRING,
        DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
)

const val EXECUTION_TIME_STRING = "01:30"
val EXECUTION_TIME: LocalTime = LocalTime.parse(EXECUTION_TIME_STRING)

const val PRICE_STRING = "120.00"
val PRICE: BigDecimal = BigDecimal(PRICE_STRING)

const val AMOUNT_STRING = "125.00"
val AMOUNT: BigDecimal = BigDecimal(AMOUNT_STRING)


const val OPEN_TIME_STRING: String = "08:00:00"
val OPEN_TIME: LocalTime = LocalTime.parse(OPEN_TIME_STRING)
const val CLOSE_TIME_STRING: String = "22:00:00"
val CLOSE_TIME: LocalTime = LocalTime.parse(CLOSE_TIME_STRING)

const val SOCCER_FIELD_NAME = "SoccerField"
const val SOCCER_FIELD_WIDTH = 40
const val SOCCER_FIELD_LENGTH = 60
const val SOCCER_FIELD_IS_LIGHTING = true
const val SOCCER_FIELD_IS_FENCED = false
const val SOCCER_FIELD_IS_LOCKER_ROOM = true
const val DESCRIPTION = "Soccer field description"

const val SURFACE_NAME = "Natural"