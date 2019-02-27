package com.pk.ysf.api.service.impl

import com.pk.ysf.api.service.spec.BookingService
import com.pk.ysf.apimodels.entity.SoccerField
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
class BookingServiceImplTest {

    @InjectMocks
    lateinit var bookingService: BookingService

    companion object {
        const val ID = 3L
        const val NAME = "Test Soccer Field"
//        const val ADDRESS =
//        const val SURFACE =
        const val WIDTH = 35
        const val LENGTH = 70
        val PRICE = BigDecimal.TEN
        const val IS_LIGHTING = true
        const val IS_FENCED = true
        const val IS_LOCKER_ROOM = true
        const val DESCRIPTION = "Test description"
    }

    private fun getCorrectSoccerField(): SoccerField {
        return SoccerField.build {
            id = ID
            name = NAME
            // address
            // surface
            width = WIDTH
            length = LENGTH
            price = PRICE
            isLighting = IS_LIGHTING
            isFenced = IS_FENCED
            isLockerRoom = IS_LOCKER_ROOM
            description = DESCRIPTION
        }
    }
}