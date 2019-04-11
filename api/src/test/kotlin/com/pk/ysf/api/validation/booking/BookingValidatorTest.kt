package com.pk.ysf.api.validation.booking

import com.pk.ysf.api.data.any
import com.pk.ysf.api.data.bookingInputMock
import com.pk.ysf.api.data.soccerFieldMock
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.exception.BookingException
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import de.jodamob.kotlin.testrunner.KotlinTestRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(KotlinTestRunner::class)
class BookingValidatorTest {

    @InjectMocks
    lateinit var bookingValidator: BookingValidator

    @Mock
    lateinit var soccerFieldRepository: SoccerFieldRepository

    @Mock
    lateinit var bookingRepository: BookingRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(soccerFieldRepository.findById(any(Long::class.java)))
                .thenReturn(Optional.ofNullable(soccerFieldMock()))
    }

    @Test
    fun soccerFieldShouldBeOpen() {
        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(emptyList())

        val result1: Boolean = bookingValidator.validate(
                prepareBookingInput("08", "00")
        )
        assertEquals(true, result1)

        val result2: Boolean = bookingValidator.validate(
                prepareBookingInput("18", "30")
        )
        assertEquals(true, result2)

        val result3: Boolean = bookingValidator.validate(
                prepareBookingInput("10", "45")
        )
        assertEquals(true, result3)

        val result4: Boolean = bookingValidator.validate(
                prepareBookingInput("12", "50")
        )
        assertEquals(true, result4)

        val result5: Boolean = bookingValidator.validate(
                prepareBookingInput("16", "25")
        )
        assertEquals(true, result5)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldBeClose() {
        val result1: Boolean = bookingValidator.validate(
                prepareBookingInput("07", "59")
        )
        assertEquals(false, result1)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldBeClose2() {
        val result2: Boolean = bookingValidator.validate(
                prepareBookingInput("20", "31")
        )
        assertEquals(false, result2)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldBeClose3() {
        val result3: Boolean = bookingValidator.validate(
                prepareBookingInput("20", "45")
        )
        assertEquals(false, result3)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldBeClose4() {
        val result4: Boolean = bookingValidator.validate(
                prepareBookingInput("22", "50")
        )
        assertEquals(false, result4)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldBeClose5() {
        val result5: Boolean = bookingValidator.validate(
                prepareBookingInput("06", "25")
        )
        assertEquals(false, result5)
    }

    private fun prepareBookingInput(startHour: String, startMinute: String): BookingInput {
        val startDate = "2019-05-05 $startHour:$startMinute"
        val executionTime = "01:30"

        return bookingInputMock(
                executionTime = executionTime,
                startDate = startDate
        )
    }
}