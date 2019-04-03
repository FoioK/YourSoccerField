package com.pk.ysf.api.service.impl

import com.pk.ysf.api.data.SOCCER_FIELD_ID
import com.pk.ysf.api.data.bookingMock
import com.pk.ysf.api.data.soccerFieldMockOptional
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import de.jodamob.kotlin.testrunner.KotlinTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(KotlinTestRunner::class)
class BookingServiceImplTest {

    @InjectMocks
    lateinit var bookingService: BookingServiceImpl

    @Mock
    lateinit var soccerFieldRepository: SoccerFieldRepository

    @Mock
    lateinit var bookingRepository: BookingRepository

    @Mock
    lateinit var  bookingInputToBooking: BookingInputToBooking

    @Mock
    lateinit var bookingToBookingDetails: BookingToBookingDetails

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(soccerFieldRepository.findById(SOCCER_FIELD_ID))
                .thenReturn(soccerFieldMockOptional())

        Mockito.`when`(bookingRepository.findAllByDate(soccerFieldId = SOCCER_FIELD_ID))
                .thenReturn(listOf(bookingMock()))
    }

    @Test
    fun shouldCreateBooking() {
//        val bookingDetails: BookingDetails = bookingService.create(correctBookingInput())
    }
}