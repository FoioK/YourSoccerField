package com.pk.ysf.api.validation.booking

import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.model.entity.OpenHour
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.exception.BookingException
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.model.exception.OpenHourException
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.util.stringToDateTime
import com.pk.ysf.api.util.stringToTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class BookingValidator @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository,
        private val bookingRepository: BookingRepository
) {

    fun validate(bookingInput: BookingInput): Boolean {
        val soccerField: SoccerField = this.getSoccerFieldById(bookingInput.soccerField)
        val bookingStartDate: LocalDateTime = stringToDateTime(bookingInput.startDate)
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
                1 -> openHour.mondayStart
                2 -> openHour.tuesdayStart
                3 -> openHour.wednesdayStart
                4 -> openHour.thursdayStart
                5 -> openHour.fridayStart
                6 -> openHour.saturdayStart
                7 -> openHour.sundayStart
                else -> throw OpenHourException(
                        "Error occurred while find open time by day of week"
                )
            }

    private fun findCloseTimeByDayOfWeek(dayOfWeek: DayOfWeek, openHour: OpenHour): LocalTime =
            when (dayOfWeek.value) {
                1 -> openHour.mondayEnd
                2 -> openHour.tuesdayEnd
                3 -> openHour.wednesdayEnd
                4 -> openHour.thursdayEnd
                5 -> openHour.fridayEnd
                6 -> openHour.saturdayEnd
                7 -> openHour.sundayEnd
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

        return if (sum.isBefore(one)) stringToTime("23:59") else sum
    }
}