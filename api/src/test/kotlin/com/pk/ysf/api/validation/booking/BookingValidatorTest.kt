package com.pk.ysf.api.validation.booking

import com.pk.ysf.api.data.*
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.model.exception.BookingException
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import de.jodamob.kotlin.testrunner.KotlinTestRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
    fun soccerFieldShouldBeClose1() {
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

    @Test
    fun soccerFieldShouldBeFree() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result1: Boolean = bookingValidator.validate(
                prepareBookingInput("09", "30")
        )
        assertEquals(true, result1)

        val result2: Boolean = bookingValidator.validate(
                prepareBookingInput("12", "30")
        )
        assertEquals(true, result2)

        val result3: Boolean = bookingValidator.validate(
                prepareBookingInput("14", "00")
        )
        assertEquals(true, result3)

        val result4: Boolean = bookingValidator.validate(
                prepareBookingInput("13", "00")
        )
        assertEquals(true, result4)

        val result5: Boolean = bookingValidator.validate(
                prepareBookingInput("20", "00")
        )
        assertEquals(true, result5)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree1() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result1: Boolean = bookingValidator.validate(
                prepareBookingInput("08", "00")
        )
        assertEquals(false, result1)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree2() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result2: Boolean = bookingValidator.validate(
                prepareBookingInput("10", "00")
        )
        assertEquals(false, result2)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree3() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result3: Boolean = bookingValidator.validate(
                prepareBookingInput("10", "29")
        )
        assertEquals(false, result3)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree4() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result4: Boolean = bookingValidator.validate(
                prepareBookingInput("14", "01")
        )
        assertEquals(false, result4)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree5() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result5: Boolean = bookingValidator.validate(
                prepareBookingInput("16", "00")
        )
        assertEquals(false, result5)
    }

    @Test(expected = BookingException::class)
    fun soccerFieldShouldNotBeFree6() {
        val bookingMock8: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 08:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock11: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))
        val bookingMock1530: Booking = bookingMock(startDate = LocalDateTime.parse(
                "2019-05-05 15:30",
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        ))

        Mockito.`when`(bookingRepository.findAllByDate(
                any(Long::class.java),
                any(Int::class.java),
                any(Int::class.java),
                any(Int::class.java)
        )).thenReturn(listOf(bookingMock8, bookingMock11, bookingMock1530))

        val result6: Boolean = bookingValidator.validate(
                prepareBookingInput("16", "59")
        )
        assertEquals(false, result6)
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