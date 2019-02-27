package com.pk.ysf.api.service.impl

import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import com.pk.ysf.api.service.spec.BookingService
import com.pk.ysf.api.util.DateUtil
import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.apimodels.entity.OpenHour
import com.pk.ysf.apimodels.entity.SoccerField
import com.pk.ysf.apimodels.exception.AppException
import com.pk.ysf.apimodels.exception.BookingException
import com.pk.ysf.apimodels.exception.ErrorCode
import com.pk.ysf.apimodels.exception.MissingEntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
open class BookingServiceImpl @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository,
        private val bookingRepository: BookingRepository,
        private val bookingInputToBooking: BookingInputToBooking,
        private val bookingToBookingDetails: BookingToBookingDetails
) : BookingService {

    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).BOOKINGS_POST_CREATE)")
    override fun create(@RequestBody bookingInput: BookingInput): BookingDetails {
        if (!this.validation(bookingInput)) {
            throw AppException(
                    "Error occurred during data validation",
                    HttpStatus.PRECONDITION_FAILED,
                    ErrorCode.INVALID_INPUT
            )
        }

        val booking: Booking = this.bookingInputToBooking.map(bookingInput)

        return this.bookingToBookingDetails.map(booking)
    }

    private fun validation(bookingInput: BookingInput): Boolean {
        val soccerField: SoccerField = this.getSoccerFieldById(bookingInput.soccerField)
        val bookingStartDate: LocalDateTime = LocalDateTime.parse(
                bookingInput.startDate,
                DateTimeFormatter.ofPattern(DateUtil.shortPattern)
        )
        val executionTime: LocalTime = LocalTime.parse(bookingInput.executionTime)

        // TODO do wyje **!!**
        val openHour: OpenHour = soccerField.openHour!!

        if (!this.checkIsSoccerFieldOpen(bookingStartDate, executionTime, openHour)) {
            return false
        }

        return this.checkIsSoccerFieldFree(
                bookingInput.soccerField,
                bookingStartDate,
                executionTime
        )
    }

    private fun getSoccerFieldById(soccerFieldId: Long): SoccerField {
        return this.soccerFieldRepository
                .findById(soccerFieldId)
                .orElseThrow {
                    MissingEntityException(
                            "Cannot find soccer field with id $soccerFieldId",
                            ErrorCode.NOT_FOUND_BY_ID
                    )
                }
    }

    private fun checkIsSoccerFieldOpen(
            bookingStartDate: LocalDateTime,
            executionTime: LocalTime,
            openHour: OpenHour
    ): Boolean {
        val dayOfWeek: DayOfWeek = bookingStartDate.dayOfWeek
        val openTime: LocalTime = findOpenTimeByDayOfWeek(dayOfWeek, openHour)
        val closeTime: LocalTime = findCloseTimeByDayOfWeek(dayOfWeek, openHour)
        val bookingStartTime: LocalTime = bookingStartDate.toLocalTime()

        if (openTime.isAfter(bookingStartTime)) {
            throw BookingException(
                    "Soccer field is not open yet",
                    ErrorCode.SOCCER_FIELD_NOT_OPEN
            )
        }

        if (closeTime.isBefore(this.sumLocalTimes(bookingStartTime, executionTime))) {
            throw BookingException(
                    "Soccer field is then closed",
                    ErrorCode.SOCCER_FIELD_CLOSED
            )
        }

        return true
    }

    // TODO kasacja *!!*
    private fun findOpenTimeByDayOfWeek(dayOfWeek: DayOfWeek, openHour: OpenHour): LocalTime {
        when (dayOfWeek.value) {
            1 -> return openHour.s1!!
            2 -> return openHour.s2!!
            3 -> return openHour.s3!!
            4 -> return openHour.s4!!
            5 -> return openHour.s5!!
            6 -> return openHour.s6!!
            7 -> return openHour.s7!!
        }

        throw AppException(
                "Error occurred while find open time by day of week",
                HttpStatus.BAD_REQUEST,
                ErrorCode.OPEN_HOUR_GET_VALUE
        )
    }

    // TODO kasacja *!!*
    private fun findCloseTimeByDayOfWeek(dayOfWeek: DayOfWeek, openHour: OpenHour): LocalTime {
        when (dayOfWeek.value) {
            1 -> return openHour.e1!!
            2 -> return openHour.e2!!
            3 -> return openHour.e3!!
            4 -> return openHour.e4!!
            5 -> return openHour.e5!!
            6 -> return openHour.e6!!
            7 -> return openHour.e7!!
        }

        throw AppException(
                "Error occurred while find close time by day of week",
                HttpStatus.BAD_REQUEST,
                ErrorCode.OPEN_HOUR_GET_VALUE
        )
    }

    private fun checkIsSoccerFieldFree(
            soccerFieldId: Long,
            bookingStartDate: LocalDateTime,
            executionTime: LocalTime
    ): Boolean {
        val bookingStartTime: LocalTime = bookingStartDate.toLocalTime()
        val bookingEndTime: LocalTime = this.sumLocalTimes(bookingStartTime, executionTime)

        val bookings: List<Booking> = this.bookingRepository
                .findAllByDate(
                        soccerFieldId,
                        bookingStartDate.year,
                        bookingStartDate.monthValue,
                        bookingStartDate.dayOfMonth
                )

        if (this.filterBookingByStartAndEndTime(bookings, bookingStartTime, bookingEndTime) > 0) {
            throw BookingException(
                    "Is another booking on this time",
                    ErrorCode.BOOKING_CONFLICT
            )
        }

        return true
    }

    private fun filterBookingByStartAndEndTime(
            bookings: List<Booking>,
            bookingStartTime: LocalTime,
            bookingEndTime: LocalTime
    ): Long {
        return bookings.stream()
                .filter { booking ->
                    val startTime: LocalTime = booking.startDate.toLocalTime()
                    val endTime: LocalTime = this.sumLocalTimes(
                            startTime,
                            booking.executionTime
                    )

                    if (bookingStartTime == startTime || bookingEndTime == endTime) {
                        return@filter true
                    }

                    if (bookingStartTime.isAfter(startTime) || bookingStartTime.isBefore(endTime)) {
                        return@filter true
                    }

                    return@filter bookingEndTime.isAfter(startTime) && bookingEndTime.isBefore(endTime)
                }
                .count()
    }

    private fun sumLocalTimes(one: LocalTime, two: LocalTime): LocalTime {
        val sum: LocalTime = one.plusHours(two.hour.toLong()).minusMinutes(two.minute.toLong())

        return if (sum.isBefore(one)) LocalTime.of(23, 59, 59, 59) else sum
    }
}