package com.pk.ysf.api.validation

const val TIME_PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\$"
const val DATE_PATTERN = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"
const val AMOUNT_PATTERN = "^(0|[1-9][0-9]*)(\\.[0-9]{2,2})?\$"