package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.util.DateUtil
import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.apimodels.entity.SoccerField
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
class BookingInputToBookingTest {

    @InjectMocks
    lateinit var bookingInputToBooking: BookingInputToBooking

    @Mock
    lateinit var soccerFieldRepository: SoccerFieldRepository

    @Before
    fun init() {
        Mockito.`when`(soccerFieldRepository.findById(SOCCER_FIELD))
                .thenReturn(getSoccerFieldMock())
    }

    private fun getSoccerFieldMock(): Optional<SoccerField> {
        val soccerField: SoccerField = SoccerField.build {
            id = SOCCER_FIELD
            name = SOCCER_FIELD_NAME
            width = SOCCER_FIELD_WIDTH
            length = SOCCER_FIELD_LENGTH
            price = SOCCER_FIELD_PRICE
            isLighting = IS_LIGHTING
            isFenced = IS_FENCED
            isLockerRoom = IS_LOCKER_ROOM
            description = DESCRIPTION
        }

        return Optional.ofNullable(soccerField)
    }

    companion object {
        const val USER_CODE = 1L
        const val START_DATE = "2019-01-01 13:00"
        const val EXECUTION_TIME = "01:30"
        const val AMOUNT = "125.00"
        const val IS_PAYED = true

        private const val SOCCER_FIELD = 1L
        private const val SOCCER_FIELD_NAME = "SoccerField"
        private const val SOCCER_FIELD_WIDTH = 40
        private const val SOCCER_FIELD_LENGTH = 60
        private const val PRICE = "120.00"
        private val SOCCER_FIELD_PRICE: BigDecimal = BigDecimal(PRICE)
        private const val IS_LIGHTING = true
        private const val IS_FENCED = false
        private const val IS_LOCKER_ROOM = true
        private const val DESCRIPTION = "Soccer field description"
    }

    @Test
    fun correctMapBookingInput() {
        val booking: Booking = bookingInputToBooking.map(correctBookingInput())

        assertEquals(USER_CODE, booking.userCode)
        assertNotNull(booking.startDate)
        assertEquals(START_DATE, booking.startDate.format(DateTimeFormatter.ofPattern(DateUtil.shortPattern)))
        assertNotNull(booking.executionTime)
        assertEquals(EXECUTION_TIME, booking.executionTime.toString())
        assertNotNull(booking.amount)
        assertEquals(AMOUNT, booking.amount.toString())
        assertEquals(IS_PAYED, booking.isPayed)

        assertEquals(SOCCER_FIELD, booking.soccerField.id)
        assertEquals(SOCCER_FIELD_NAME, booking.soccerField.name)
        assertEquals(SOCCER_FIELD_WIDTH, booking.soccerField.width)
        assertEquals(SOCCER_FIELD_LENGTH, booking.soccerField.length)
        assertEquals(SOCCER_FIELD_PRICE, booking.soccerField.price)
        assertEquals(IS_LIGHTING, booking.soccerField.isLighting)
        assertEquals(IS_FENCED, booking.soccerField.isFenced)
        assertEquals(IS_LOCKER_ROOM, booking.soccerField.isLockerRoom)
        assertEquals(DESCRIPTION, booking.soccerField.description)
    }

    @Test
    fun correctMapAll() {
        val bookings: Collection<Booking> = bookingInputToBooking.mapAll(
                listOf(correctBookingInput(), correctBookingInput(), correctBookingInput())
        )

        bookings.forEach {
            assertEquals(USER_CODE, it.userCode)
            assertNotNull(it.startDate)
            assertEquals(START_DATE, it.startDate.format(DateTimeFormatter.ofPattern(DateUtil.shortPattern)))
            assertNotNull(it.executionTime)
            assertEquals(EXECUTION_TIME, it.executionTime.toString())
            assertNotNull(it.amount)
            assertEquals(AMOUNT, it.amount.toString())
            assertEquals(IS_PAYED, it.isPayed)
            assertEquals(SOCCER_FIELD, it.soccerField.id)
        }
    }

    private fun correctBookingInput(): BookingInput {
        return BookingInput.build {
            userCode = USER_CODE
            startDate = START_DATE
            executionTime = EXECUTION_TIME
            amount = AMOUNT
            isPayed = IS_PAYED
            soccerField = SOCCER_FIELD
        }
    }

    @Test(expected = DateTimeParseException::class)
    fun badDateFormatTest() {
        val badStartDateFormat = "2019-01-01T13:00"
        val incorrectBookingInput: BookingInput = BookingInput.build {
            startDate = badStartDateFormat
        }
        bookingInputToBooking.map(incorrectBookingInput)
    }

    @Test(expected = DateTimeParseException::class)
    fun badDateFormatTest2() {
        val badStartDateFormat = "2019-01-01"
        val incorrectBookingInput: BookingInput = BookingInput.build {
            startDate = badStartDateFormat
        }
        bookingInputToBooking.map(incorrectBookingInput)
    }

    @Test(expected = DateTimeParseException::class)
    fun badExecutionTimeTest() {
        val badExecutionTime = "1:50"
        val incorrectBookingInput: BookingInput = BookingInput.build {
            executionTime = badExecutionTime
        }
        bookingInputToBooking.map(incorrectBookingInput)
    }
}