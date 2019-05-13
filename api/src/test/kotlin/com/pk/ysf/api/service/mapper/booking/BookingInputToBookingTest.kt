package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.data.*
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import org.junit.Before
import kotlin.test.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner
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
        Mockito.`when`(soccerFieldRepository.findById(SOCCER_FIELD_ID))
                .thenReturn(Optional.ofNullable(soccerFieldMock()))
    }

    @Test
    fun correctMapBookingInput() {
        val booking: Booking = bookingInputToBooking.map(bookingInputMock())

        assertEquals(USER_CODE, booking.userCode)
        assertNotNull(booking.startDate)
        assertEquals(START_DATE_STRING, booking.startDate.format(DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)))
        assertNotNull(booking.executionTime)
        assertEquals(EXECUTION_TIME_STRING, booking.executionTime.toString())
        assertNotNull(booking.amount)
        assertEquals(AMOUNT_STRING, booking.amount.toString())
        assertEquals(IS_PAYED, booking.isPayed)

        assertEquals(SOCCER_FIELD_ID, booking.soccerField.id)
        assertEquals(SOCCER_FIELD_NAME, booking.soccerField.name)
        assertEquals(SOCCER_FIELD_WIDTH, booking.soccerField.width)
        assertEquals(SOCCER_FIELD_LENGTH, booking.soccerField.length)
        assertEquals(PRICE, booking.soccerField.price)
        assertEquals(SOCCER_FIELD_IS_LIGHTING, booking.soccerField.isLighting)
        assertEquals(SOCCER_FIELD_IS_FENCED, booking.soccerField.isFenced)
        assertEquals(SOCCER_FIELD_IS_LOCKER_ROOM, booking.soccerField.isLockerRoom)
        assertEquals(DESCRIPTION, booking.soccerField.description)
    }

    @Test
    fun correctMapAll() {
        val bookings: Collection<Booking> = bookingInputToBooking.mapAll(
                listOf(bookingInputMock(), bookingInputMock(), bookingInputMock())
        )

        bookings.forEach {
            assertEquals(USER_CODE, it.userCode)
            assertNotNull(it.startDate)
            assertEquals(START_DATE_STRING, it.startDate.format(DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)))
            assertNotNull(it.executionTime)
            assertEquals(EXECUTION_TIME_STRING, it.executionTime.toString())
            assertNotNull(it.amount)
            assertEquals(AMOUNT_STRING, it.amount.toString())
            assertEquals(IS_PAYED, it.isPayed)
            assertEquals(SOCCER_FIELD_ID, it.soccerField.id)
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