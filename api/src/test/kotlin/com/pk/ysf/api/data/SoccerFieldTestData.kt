package com.pk.ysf.api.data

import com.pk.ysf.apimodels.entity.SoccerField
import java.util.*

fun soccerFieldMock(): SoccerField =
        SoccerField.build {
            id = SOCCER_FIELD_ID
            name = SOCCER_FIELD_NAME
            width = SOCCER_FIELD_WIDTH
            length = SOCCER_FIELD_LENGTH
            price = PRICE
            isLighting = SOCCER_FIELD_IS_LIGHTING
            isFenced = SOCCER_FIELD_IS_FENCED
            isLockerRoom = SOCCER_FIELD_IS_LOCKER_ROOM
            description = DESCRIPTION
            openHour = openHourMock()
        }

fun soccerFieldMockOptional(): Optional<SoccerField> =
        Optional.ofNullable(soccerFieldMock())