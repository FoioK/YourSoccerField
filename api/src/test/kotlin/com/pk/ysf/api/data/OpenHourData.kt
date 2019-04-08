package com.pk.ysf.api.data

import com.pk.ysf.api.model.entity.OpenHour

fun openHourMock(): OpenHour =
        OpenHour.build {
            id = OPEN_HOUR_ID

            s1 = OPEN_TIME
            s2 = OPEN_TIME
            s3 = OPEN_TIME
            s4 = OPEN_TIME
            s5 = OPEN_TIME
            s6 = OPEN_TIME
            s7 = OPEN_TIME

            e1 = CLOSE_TIME
            e2 = CLOSE_TIME
            e3 = CLOSE_TIME
            e4 = CLOSE_TIME
            e5 = CLOSE_TIME
            e6 = CLOSE_TIME
            e7 = CLOSE_TIME
        }