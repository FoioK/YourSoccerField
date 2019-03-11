package com.pk.ysf.api.service.impl

import com.pk.ysf.api.annotation.ValidInput
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import com.pk.ysf.api.service.spec.BookingService
import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.apimodels.entity.OpenHour
import com.pk.ysf.apimodels.entity.SoccerField
import com.pk.ysf.apimodels.exception.BookingException
import com.pk.ysf.apimodels.exception.MissingEntityException
import com.pk.ysf.apimodels.exception.OpenHourException
import com.pk.ysf.apimodels.exception.ValidationException
import org.springframework.beans.factory.annotation.Autowired
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
    @ValidInput
    override fun create(@RequestBody bookingInput: BookingInput): BookingDetails {
        if (!this.validation(bookingInput)) {
            throw ValidationException("Error occurred during data validation")
        }

        val booking: Booking = this.bookingInputToBooking.map(bookingInput)

        return this.bookingToBookingDetails.map(booking)
    }

    private fun validation(bookingInput: BookingInput): Boolean {
        val soccerField: SoccerField = this.getSoccerFieldById(bookingInput.soccerField)
        val bookingStartDate: LocalDateTime = LocalDateTime.parse(
                bookingInput.startDate,
                DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
        )
        val executionTime: LocalTime = LocalTime.parse(bookingInput.executionTime)
        val openHour: OpenHour = soccerField.openHour

        if (!this.checkIsSoccerFieldOpen(bookingStartDate, executionTime, openHour)) {
            return false
        }

        return this.checkIsSoccerFieldFree(
                bookingInput.soccerField,
                bookingStartDate,
                executionTime
        )
    }

    private fun getSoccerFieldById(soccerFieldId: Long): SoccerField =
            this.soccerFieldRepository
                    .findById(soccerFieldId)
                    .orElseThrow {
                        MissingEntityException("Cannot find soccer field with id $soccerFieldId")
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
            throw BookingException("Soccer field is not open yet")
        }

        if (closeTime.isBefore(this.sumLocalTimes(bookingStartTime, executionTime))) {
            throw BookingException("Soccer field is then closed")
        }

        return true
    }

    private fun findOpenTimeByDayOfWeek(dayOfWeek: DayOfWeek, openHour: OpenHour): LocalTime =
            when (dayOfWeek.value) {
                1 -> openHour.s1
                2 -> openHour.s2
                3 -> openHour.s3
                4 -> openHour.s4
                5 -> openHour.s5
                6 -> openHour.s6
                7 -> openHour.s7
                else -> throw OpenHourException(
                        "Error occurred while find open time by day of week"
                )
            }

    private fun findCloseTimeByDayOfWeek(dayOfWeek: DayOfWeek, openHour: OpenHour): LocalTime =
            when (dayOfWeek.value) {
                1 -> openHour.e1
                2 -> openHour.e2
                3 -> openHour.e3
                4 -> openHour.e4
                5 -> openHour.e5
                6 -> openHour.e6
                7 -> openHour.e7
                else -> throw OpenHourException(
                        "Error occurred while find close time by day of week"
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

        if (this.findConflictWithExistBooking(bookings, bookingStartTime, bookingEndTime) > 0) {
            throw BookingException("Is another booking on this time")
        }

        return true
    }

    private fun findConflictWithExistBooking(
            bookings: List<Booking>,
            bookingStartTime: LocalTime,
            bookingEndTime: LocalTime
    ): Long = bookings.stream()
            .filter { booking ->
                val startTime: LocalTime = booking.startDate.toLocalTime()
                val endTime: LocalTime = this.sumLocalTimes(
                        startTime,
                        booking.executionTime
                )

                if (bookingStartTime == startTime || bookingEndTime == endTime) {
                    return@filter true
                }

                if (bookingStartTime.isAfter(startTime) && bookingStartTime.isBefore(endTime)) {
                    return@filter true
                }

                return@filter bookingEndTime.isAfter(startTime) && bookingEndTime.isBefore(endTime)
            }
            .count()

    private fun sumLocalTimes(one: LocalTime, two: LocalTime): LocalTime {
        val sum: LocalTime = one.plusHours(two.hour.toLong()).plusMinutes(two.minute.toLong())

        return if (sum.isBefore(one)) LocalTime.of(23, 59, 59, 59) else sum
    }
}