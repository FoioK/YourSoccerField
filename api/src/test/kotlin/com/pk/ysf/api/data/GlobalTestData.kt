package com.pk.ysf.api.data

import com.pk.ysf.api.util.DateUtil
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val IS_PAYED = true

const val SOCCER_FIELD_ID = 1L
const val BOOKING_ID = 3L
const val USER_CODE = 1L

const val START_DATE_STRING = "2019-05-05 11:00"
val START_DATE: LocalDateTime = LocalDateTime.parse(
        START_DATE_STRING,
        DateTimeFormatter.ofPattern(DateUtil.shortPattern)
)

const val EXECUTION_TIME_STRING = "02:30"
val EXECUTION_TIME: LocalTime = LocalTime.parse(EXECUTION_TIME_STRING)

const val PRICE_STRING = "120.00"
val PRICE: BigDecimal = BigDecimal(PRICE_STRING)

const val AMOUNT_STRING = "125.00"
val AMOUNT: BigDecimal = BigDecimal(AMOUNT_STRING)


const val SOCCER_FIELD_NAME = "SoccerField"
const val SOCCER_FIELD_WIDTH = 40
const val SOCCER_FIELD_LENGTH = 60
const val SOCCER_FIELD_IS_LIGHTING = true
const val SOCCER_FIELD_IS_FENCED = false
const val SOCCER_FIELD_IS_LOCKER_ROOM = true
const val DESCRIPTION = "Soccer field description"