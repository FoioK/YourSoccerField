import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.apimodels.entity.SoccerField
import com.pk.ysf.repository.SoccerFieldRepository
import com.pk.ysf.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.util.DateUtil
import org.junit.Before
import org.junit.Test
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
        Mockito.`when`(soccerFieldRepository.findById(SOCCER_FIELD))
                .thenReturn(mockSoccerField())
    }

    // TODO jak będzie builder w soccerField.kt to dodać budowanie wraz z id i sprawdzać to w tescie
    fun mockSoccerField(): Optional<SoccerField> {
        val soccerField: SoccerField = SoccerField()
//        soccerField.id = SOCCER_FIELD

        return Optional.ofNullable(soccerField)
    }

    companion object {
        const val USER_CODE = 1L
        const val START_DATE = "2019-01-01 13:00"
        const val EXECUTION_TIME = "01:30"
        const val AMOUNT = "125.00"
        const val IS_PAYED = true
        const val SOCCER_FIELD = 1L
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
        // TODO dodpisać test po dodaniu implementacji mockSoccerField()
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
            // TODO dodpisać test po dodaniu implementacji mockSoccerField()
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